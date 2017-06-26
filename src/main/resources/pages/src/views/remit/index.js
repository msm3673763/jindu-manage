var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var FormItem = UcsmyUI.Form.FormItem;
var Grid = require('../widget/other/grid');
var SelectDropDown = UcsmyUI.SelectDropDown;
var DatePicker = UcsmyUI.DatePicker;
var option = [
    {option: '未认证', value: '1'},
    {option: '待拨款', value: '2'},
    {option: '已拨款', value: '3'},
    {option: '已认证', value: '4'},
    {option: '认证失败', value: '5'},
    {option: '已解绑', value: '6'}
];
myPanel = React.createClass({

    getInitialState:function () {

        return{
            countResult:''
        }
    },
    _reload: function () {
        this.refs.grid.reload();
    },
    _edit: function (item) {

        var me = this;
        UcsmyIndex.confirm("确定", "确定要把该数据置为待拨款么？", function() {
            $.post('enterpriseAccount/updateCorporDepositNoStatus', {"recordId": item.recordId}, function (result) {
                if (result && result.retcode && result.retcode == '0') {
                    UcsmyIndex.alert("成功", "更新成功");
                    me._reload();
                } else {
                    UcsmyIndex.alert("失败", result.retmsg);
                }
            });
        });

    },
    componentDidMount:function()
    {
        var me = this;
        $.ajax({
           url:'enterpriseAccount/countCompany',
           dataType:'JSON',
           type:'GET',
           success:function(result)
           {
               if(result.retcode==='0')
               {
                   me.setState({countResult:result.data});
               }
           },
            error:function()
            {

            }
        });
    },
    _search: function () {
        this.refs.grid.load({
            status: this.refs.status.getValue(),
            startDate: this.refs.startDate.getValue(),
            endDate: this.refs.endDate.getValue(),
            depositor: this.refs.depositor.getValue()
        });
    },
    render: function() {
        var me = this;

        return (
            <div>
                <h2 className="content-title">企业认证</h2>

                <div className="panel">
                    <div className="panel-content">
                        <FormItem label="已打款企业"><span>{this.state.countResult.countCompany}</span></FormItem>
                        <FormItem label="已打款帐号"><span>{this.state.countResult.countAccountNo}</span></FormItem>
                    </div>
                </div>

                <div className="panel">
                    <div className="panel-title">查询条件</div>
                    <div className="panel-content">
                        <FormItem label="状态">
                            <SelectDropDown ref="status" defaultText="请选择" defaultValue="" option={option} searchPlaceholder="请选择" />
                        </FormItem>

                        <FormItem label="更新时间">
                            <DatePicker ref="startDate" name="startDate" format="yyyy-mm-dd" time="true" placeholder="开始时间"/>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;&nbsp;
                            <DatePicker ref="endDate" name="endDate" format="yyyy-mm-dd" time="true" placeholder="结束时间"/>
                        </FormItem>
                        <FormItem label="企业名称"><Input ref="depositor" placeholder="输入企业名称"/></FormItem>
                    </div>
                </div>
                <div className="btn-panel">
                    <Button buttonType="bidnow" onClick={this._search}>查询</Button>
                    <Button buttonType="bidnow" onClick={this._add}>添加</Button>
                </div>

                <div className="table-panel">
                    <Grid url="enterpriseAccount/query" ref = "grid"
                          isTextOverflowHidden={true}
                          columns={[
                            {name: 'depositor', header: '企业名称', width: 150},
                            {name: 'depositNo', header: '银行帐号', width: 150},
                            {name: 'depositBank', header: '所属银行', width: 150},
                            {name: 'depositBankBranch', header: '开户行', width: 150},
                            {name: 'updateTime', header: '更新时间', width: 200},
                            {name: 'memo', header: '打款信息', width: 150},
                            {name: 'authStatus', header: '认证状态' , content:function(column){
                                var html = "";
                                switch (column.authStatus)
                                {
                                  case  1:
                                    html='未认证';
                                  break;
                                  case  2:
                                    html='待拨款';
                                  break;
                                  case  3:
                                    html='已拨款';
                                  break;
                                  case  4:
                                    html='已认证';
                                  break;
                                  case  5:
                                    html='认证失败';
                                  break;
                                  case 6:
                                    html='已解绑';
                                  break;
                                }
                                return <span>{html}</span>
                            } },
                            {name: 'id', header: '操作', width: 200, content: function (item) {
                               var status = item.authStatus;
                               if(status==3)
                               {
                                return (
                                    <span>
                                        <a href="Javascript:void(0);" onClick={me._edit.bind(this, item)}>设为“待拨款”</a>
                                    </span>
                                )
                               }
                            }}
                        ]}>
                    </Grid>
                    <div className="clearfix"></div>
                </div>
            </div>
        );
    }
});