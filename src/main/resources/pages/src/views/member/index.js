var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var FormItem = UcsmyUI.Form.FormItem;
var Grid = require('../widget/other/grid');

myPanel = React.createClass({
    _reload: function () {
        this.refs.grid.reload();
    },

    _search: function () {
        this.refs.grid.load({
            name: this.refs.username.getValue(),
        });
    },
    render: function() {
        return (
            <div>
                <h2 className="content-title">金度用户管理</h2>
                <div className="panel">
                    <div className="panel-title">查询条件</div>
                    <div className="panel-content">
                        <FormItem label="用户名"><Input ref="username"/></FormItem>
                    </div>
                </div>
                <div className="btn-panel">
                    <Button buttonType="bidnow" onClick={this._search}>查询</Button>
                </div>

                <div className="table-panel">
                    <Grid url="user/getMembers" ref = "grid"
                          isTextOverflowHidden={true}
                          columns={[
                            {name: 'name', header: '用户名称'},
                            {name: 'telephone', header: '工作电话'},
                            {name: 'mobilephone', header: '移动电话'},
                            {name: 'email', header: '邮箱'},
                            {name: 'openId', header: 'openId'},
                            {name: 'createDate', header: '创建时间'}
                        ]}>
                    </Grid>
                    <div className="clearfix"></div>
                </div>
            </div>
        );
    }
});