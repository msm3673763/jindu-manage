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
            ref.init('template/addTemplate', '添加模板', {}, function() {
            	me._reload();
            });
        });
    },
    _edit: function (item) {
        var me = this;
        UcsmyIndex.openChildrenPage(Form, function (ref) {
            ref.init('template/updateTemplate', '修改模板', item, function() {
            	me._reload();
            });
        });
    },
    _delete: function (item) {
        var me = this;
        UcsmyIndex.confirm("确定", "你真的要删除该数据吗？", function() {
        	$.post('template/deleteTemplate', {templateId: item.id}, function (result) {
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
            title: this.refs.title.getValue()
        });
    },
    render: function() {
        var me = this;

        return (
            <div>
                <h2 className="content-title">模板管理</h2>
                <div className="panel">
                    <div className="panel-title">查询条件</div>
                    <div className="panel-content">
                        <FormItem label="模板主题"><Input ref="title"/></FormItem>
                    </div>
                </div>
                <div className="btn-panel">
                    <Button buttonType="bidnow" onClick={this._search}>查询</Button>
                    <Button buttonType="bidnow" onClick={this._add}>添加</Button>
                </div>
                <div className="table-panel">
                    <Grid url="template/queryTemplateList" ref = "grid"
                          isTextOverflowHidden={true}
                          pageSize={10}
                          retDataProperty='data.resultList'
                          retTotalProperty='data.totalCount'
                          retCurrentProperty='data.pageNo'
                          columns={[
                            {name: 'id', header: '模板编号', width: 150},
                            {name: 'title', header: '模板主题', width: 150},
                            {name: 'content', header: '模板内容', width: 300},
                            {name: 'typeName', header: '模板类型', width: 100},
                            {name: 'description', header: '描述', width: 200},
                            {name: 'oper', header: '操作', content: function (item) {
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