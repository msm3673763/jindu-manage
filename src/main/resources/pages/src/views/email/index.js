var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var FormItem = UcsmyUI.Form.FormItem;
var Grid = require('../widget/other/grid');
var Form = require('./other/form');

myPanel = React.createClass({
    _reload: function () {
        this.refs.grid.reload();
    },
    _add: function () {
        var me = this;
        UcsmyIndex.openChildrenPage(Form, function (ref) {
            ref.init('email/add', '添加邮箱域名关系', {}, function() {
            	me._reload();
            });
        });
    },
    _edit: function (item) {
        var me = this;
        UcsmyIndex.openChildrenPage(Form, function (ref) {
            ref.init('email/update', '修改邮箱域名关系', item, function() {
            	me._reload();
            });
        });
    },
    _delete: function (item) {
        var me = this;
        UcsmyIndex.confirm("确定", "你真的要删除该数据吗？", function() {
        	$.post('email/del', {uuid: item.uuid}, function (result) {
                if (result && result.retcode && result.retcode == '0') {
                    UcsmyIndex.alert("成功", result.retmsg);
                    me._reload();
                } else {
                    UcsmyIndex.alert("失败", result.retmsg);
                }
            });
		});
    },
    _search: function () {
        this.refs.grid.load({
            name: this.refs.name.getValue()
        });
    },
    render: function() {
        var me = this;

        return (
            <div>
                <h2 className="content-title">邮箱域名管理</h2>
                <div className="panel">
                    <div className="panel-title">查询条件</div>
                    <div className="panel-content">
                        <FormItem label="邮箱名称"><Input ref="name"/></FormItem>
                    </div>
                </div>
                <div className="btn-panel">
                    <Button buttonType="bidnow" onClick={this._search}>查询</Button>
                    <Button buttonType="bidnow" onClick={this._add}>添加</Button>
                </div>

                <div className="table-panel">
                    <Grid url="email/query" ref = "grid"
                          isTextOverflowHidden={true}
                          columns={[
                            {name: 'name', header: '邮箱名称'},
                            {name: 'domainName', header: '域名', width: 100},
                            {name: 'domainUrl', header: '登录首页', width: 300},
                            {name: 'des', header: '描述', width: 300},
                            {name: 'id', header: '操作', width: 200, content: function (item) {
                                return (
                                    <span>
                                        <a href="Javascript:void(0);" onClick={me._edit.bind(this, item)}>修改</a>
                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                        <a href="Javascript:void(0);" onClick={me._delete.bind(this, item)}>删除</a>
                                    </span>
                                )
                            }}
                        ]}>
                    </Grid>
                    <div className="clearfix"></div>
                </div>
            </div>
        );
    }
});