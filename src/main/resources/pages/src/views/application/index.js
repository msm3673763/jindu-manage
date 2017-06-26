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
            ref.init('application/add', '应用管理', {}, function() {
            	me._reload();
            });
        });
    },
    _edit: function (item) {
        var me = this;
        UcsmyIndex.openChildrenPage(Form, function (ref) {

            ref.init('application/update', '修改桌面应用', item, function() {
                me._reload();
            });

        });
    },
    _delete: function (item) {
        var me = this;
        UcsmyIndex.confirm("确定", "你真的要删除该数据吗？", function() {
        	$.post('application/delete', {uuid: item.uuid}, function (result) {
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
            applyName: this.refs.applyName.getValue(),
        });
    },
    render: function() {
        var me = this;

        return (
            <div>
                <h2 className="content-title">桌面应用管理</h2>
                <div className="panel">
                    <div className="panel-title">查询条件</div>
                    <div className="panel-content">
                        <FormItem label="应用名称"><Input ref="applyName"/></FormItem>
                    </div>
                </div>
                <div className="btn-panel">
                    <Button buttonType="bidnow" onClick={this._search}>查询</Button>
                    <Button buttonType="bidnow" onClick={this._add}>添加</Button>
                </div>

                <div className="table-panel">
                    <Grid url="application/query" ref = "grid"
                          isTextOverflowHidden={true}
                          columns={[
                            {name: 'applyName', header: '应用名称'},
                            {name: 'applyDes', header: '应用描述'},
                            {name: 'applyUrl', header: '应用链接'},
                            {name: 'status', header: '是否展示',
                                content:function(column){
                                    return (<span>
                                    {column.status === '0' ? '否' : '是'}
                                </span>)
                                }
                            },
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