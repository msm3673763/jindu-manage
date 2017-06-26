var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var FormItem = UcsmyUI.Form.FormItem;
var Grid = require('../widget/other/grid');
var Form = require('./other/form');


myPanel = React.createClass({
    _reload: function () {
        this.refs.grid.reload();
    },
    _search: function () {
        this.refs.grid.load({
            orderNumber: this.refs.orderNumber.getValue()
        });
    },
    _edit: function(item) {
        var me = this;
        UcsmyIndex.openChildrenPage(Form, function (ref) {
            ref.init('', '查看反馈意见', item, function() {
                me._reload();
            });
        });
    },
    render: function() {
        var me = this;

        return (
            <div>
                <h2 className="content-title">反馈意见管理</h2>
                <div className="panel">
                    <div className="panel-title">查询条件</div>
                    <div className="panel-content">
                        <FormItem label="工单号"><Input ref="orderNumber"/></FormItem>
                    </div>
                </div>
                <div className="btn-panel">
                    <Button buttonType="bidnow" onClick={this._search}>查询</Button>
                </div>

                <div className="table-panel">
                    <Grid url="feedback/query" ref = "grid"
                          isTextOverflowHidden={true}
                          columns={[
                            {name: 'orderNumber', header: '工单号', width: 100},
                            {name: 'createUser', header: '提交人', width: 100},
                            {name: 'contact', header: '联系电话', width: 100},
                            {name: 'createDate', header: '提交时间', width: 100, content: function(item) {
                                return (
                                    <span>{formatDate(item.createDate)}</span>
                                )
                            }},
                            {name: 'processUser', header: '处理人', width: 100},
                            {name: 'processDate', header: '处理时间', width: 100, content: function(item) {
                                return (
                                    <span>{formatDate(item.processDate)}</span>
                                )
                            }},
                            {name: 'id', header: '操作', width: 200, content: function (item) {
                                return (
                                    <span>
                                        <a href="Javascript:void(0);" onClick={me._edit.bind(this, item)}>查看</a>
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