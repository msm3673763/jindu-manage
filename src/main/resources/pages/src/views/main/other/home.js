var Home = React.createClass({
	componentDidMount: function() {
		UcsmyIndex.addContentClassName("welcome");
	},
	render: function(){
		return (
			<div className="welcome-center">
				<img src="images/wangjin-icon-big.png" />
				<p>金度后台管理系统<br />欢迎您！</p>
			</div>
        );
	 }
});
module.exports = Home;