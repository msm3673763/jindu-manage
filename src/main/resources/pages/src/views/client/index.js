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
            ref.init('config/addConfig', '添加客户端', {}, function() {
                me._reload();
            });
        });
    },
    _download: function (item) {
        window.location.href = "client/download/" + item.id;
    },
    _delete: function (item) {
        var me = this;
        UcsmyIndex.confirm("确定", "你真的要删除该参数数据吗？", function() {
            $.post('client/delete', {id: item.id}, function (result) {
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
                <h2 className="content-title">客户端管理</h2>
                <div className="panel">
                    <div className="panel-title">查询条件</div>
                    <div className="panel-content">
                        <FormItem label="客户端名称"><Input ref="name"/></FormItem>
                    </div>
                </div>
                <div className="btn-panel">
                    <Button buttonType="bidnow" onClick={this._search}>查询</Button>
                    <Button buttonType="bidnow" onClick={this._add}>添加</Button>
                </div>

                <div className="table-panel">
                    <Grid url="client/query" ref = "grid"
                          isTextOverflowHidden={true}
                          columns={[
                            {name: 'name', header: '文件名称', width: 100},
                            {name: 'md5', header: 'MD5', width: 280},
                            {name: 'description', header: '描述', width: 200},
                            {name: 'createDate', header: '上传日期', width: 150, content: function(item) {
                                return (
                                    <span>{formatDate(item.createDate)}</span>
                                )
                            }},
                            {name: 'id', header: '操作', width: 200, content: function (item) {
                                return (
                                    <span>
                                        <a href="Javascript:void(0);" onClick={me._download.bind(this, item)}>下载</a>
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