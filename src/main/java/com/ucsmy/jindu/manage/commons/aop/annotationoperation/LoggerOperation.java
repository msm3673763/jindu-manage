package com.ucsmy.jindu.manage.commons.aop.annotationoperation;


import com.ucsmy.jindu.manage.commons.aop.annotation.Logger;
import com.ucsmy.jindu.manage.commons.base.utils.StringUtils;
import com.ucsmy.jindu.manage.config.log4j2.LogCommUtil;
import com.ucsmy.jindu.manage.config.log4j2.LogOuputTarget;
import com.ucsmy.jindu.manage.manage.entity.ManageLogInfo;
import com.ucsmy.jindu.manage.manage.service.ManageLogInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Logger切面。<br>
 *
 * 2017/6/6 修改：不使用ThreadContext存储ip信息，改用session存储。<br>
 * Modified by chenqilin on 2017/6/6. <br>
 */
@Aspect
@Component
public class LoggerOperation {

	private final org.apache.logging.log4j.Logger log = LogManager.getLogger();

	@Autowired
	private ManageLogInfoService manageLogInfoService;

	/**
	 * 拦截service层的@Logger
	 * @param pjp
	 * @param logger
	 * @return
	 * @throws Throwable
	 */
	@Around("execution (* com.ucsmy.jindu.manage.manage.service..*.*(..)) && @annotation(logger)")
	public Object aroundService(ProceedingJoinPoint pjp, Logger logger) throws Throwable {
		String sessionId = LogCommUtil.getNewServiceSessionId();
		Signature signature = pjp.getSignature();
		// 日志信息
		StringBuilder logInfo = new StringBuilder();
		logInfo.append(LogCommUtil.LINE_SEPERATOR).append(LogCommUtil.OPERATE_METHOD).append(LogCommUtil.getMethodName(signature));
		if (StringUtils.isNotEmpty(logger.operationName())) {
			logInfo.append(LogCommUtil.LINE_SEPERATOR).append(LogCommUtil.OPERATE_NAME).append(logger.operationName());
		}
		if (StringUtils.isNotEmpty(logger.operationType())) {
			logInfo.append(LogCommUtil.LINE_SEPERATOR).append(LogCommUtil.OPERATE_TYPE).append(logger.operationType());
		}
		logInfo.append(LogCommUtil.LINE_SEPERATOR).append(LogCommUtil.IP_INFO).append(LogCommUtil.getSessionIpInfo());
		logInfo.append(LogCommUtil.LINE_SEPERATOR).append(LogCommUtil.USER_INFO).append(LogCommUtil.getUserInfo());
		// 入参信息
		if (logger.printInput()) {
			logInfo.append(LogCommUtil.getInputParamList(signature, pjp.getArgs()));
		}
		ThreadContext.put(sessionId, logInfo.toString());
		// 是否输出SQL
		ThreadContext.put(LogCommUtil.getSQLOutputKey(sessionId), String.valueOf(logger.printSQL()));
		// 是否输出到DB
		if (LogOuputTarget.DATABASE.equals(logger.outputTarget())) {
			ThreadContext.put(LogCommUtil.getDBOutputKey(sessionId), Boolean.toString(true));
		}
		// 是否输出出参信息
		if (logger.printOutput()) {
			ThreadContext.put(LogCommUtil.getPrintOutputKey(sessionId), Boolean.toString(true));
		}
		Object retObj = pjp.proceed();
		// 输出到日志文件
		StringBuilder info = new StringBuilder(ThreadContext.get(sessionId));
		// 如果有输出的SQL，添加到日志记录里
		info.append(LogCommUtil.LINE_SEPERATOR).append(LogCommUtil.LOG_SQL).append(ThreadContext.get(LogCommUtil.getLogSQLKey(sessionId)));
		if (Boolean.parseBoolean(ThreadContext.get(LogCommUtil.getPrintOutputKey(sessionId)))) {
			info.append(LogCommUtil.getOutputParam(signature, retObj));
		}
		log.info(info.toString());
		// 输出到数据库
		logDBOutput(Boolean.parseBoolean(ThreadContext.get(LogCommUtil.getDBOutputKey(sessionId))), info.toString());
		LogCommUtil.removeThreadContext(sessionId);
        return retObj;
	}

	/**
	 * 拦截service层使用了@Logger的抛异常的方法，打印抛异常前的日志信息
	 */
	@Pointcut("execution (* com.ucsmy.jindu.manage.manage.service..*.*(..)) && @annotation(com.ucsmy.jindu.manage.commons.aop.annotation.Logger)")
	private void exceptionPointCut(){
		// pointCut Signature
	}

	/**
	 * 发生异常时要先把ThreadContext里的已有的log信息输出 <br>
	 * 
	 * @param joinPoint
	 */
	@AfterThrowing(pointcut = "exceptionPointCut()", throwing = "e")
	public void handlerServiceException(JoinPoint joinPoint, Exception e) {
		String sessionId = LogCommUtil.getServiceSessionId();
		if (StringUtils.isNotEmpty(ThreadContext.get(sessionId))) {
			StringBuilder info = new StringBuilder(ThreadContext.get(sessionId));
			info.append(LogCommUtil.LINE_SEPERATOR).append(LogCommUtil.LOG_SQL).append(ThreadContext.get(LogCommUtil.getLogSQLKey(sessionId)));
			log.info(info.toString());
			// 输出到数据库
			logDBOutput(Boolean.parseBoolean(ThreadContext.get(LogCommUtil.getDBOutputKey(sessionId))), info.toString());
			LogCommUtil.removeThreadContext(sessionId);
		}
	}

	/**
	 * 是否输出日志到数据库
	 * @param dbOutput 是否输出到数据库
	 */
	private void logDBOutput(Boolean dbOutput, String logInfo) {
		if (dbOutput) {
			ManageLogInfo manageLogInfo = LogCommUtil.getManageLogInfo(logInfo);
			manageLogInfoService.addManageLogInfo(manageLogInfo);
		}
	}

}
