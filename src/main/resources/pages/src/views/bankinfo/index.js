var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var FormItem = UcsmyUI.Form.FormItem;
var Grid = require('../widget/other/grid');
var SelectDropDown = UcsmyUI.SelectDropDown;
var option = [
    {option: '启用', value: '1'},
    {option: '禁用', value: '2'}
];
myPanel = React.createClass({
    _reload: function () {
        this.refs.grid.reload();
    },
    _using: function (item) {
        var me = this;
        UcsmyIndex.confirm("确定", "确认要启用该银行么？", function() {
            $.post('bankInfo/using', item, function (result) {
                if (result && result.retcode && result.retcode == '0') {
                    UcsmyIndex.alert("成功", result.retmsg);
                    me._reload();
                } else {
                    UcsmyIndex.alert("失败", result.retmsg);
                }
            });
        });
    },
    _unusing:function(item)
    {
        var me = this;
        UcsmyIndex.confirm("确定", "确认要禁用该银行么？", function() {
            $.post('bankInfo/unUsing', item, function (result) {
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
            name: this.refs.name.getValue(),
            code: this.refs.code.getValue(),
            status: this.refs.status.getValue(),
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
                        <FormItem label="银行代码"><Input ref="code"/></FormItem>
                        <FormItem label="银行名称"><Input ref="name"/></FormItem>
                        <FormItem label="状态">
                            <SelectDropDown ref="status" defaultText="请选择" defaultValue="" option={option} searchPlaceholder="请选择" />
                        </FormItem>
                    </div>
                </div>
                <div className="btn-panel">
                    <Button buttonType="bidnow" onClick={this._search}>查询</Button>
                </div>

                <div className="table-panel">
                    <Grid url="bankInfo/query" ref = "grid"
                          isTextOverflowHidden={true}
                          columns={[
                            {name: 'code', header: '银行代码'},
                            {name: 'name', header: '银行名称', width: 200},
                            {name: 'abbreviation', header: '银行英文缩写'},
                            {name: 'status', header: '状态', content:function(column)
                            {
                                return (<span>
                                {column.status === '1' ? '启用' : '禁用'}
                            </span>)
                            }},
                            {name: 'id', header: '操作', width: 200, content: function (item) {
                                return (
                                    <span>
                                        <a href="Javascript:void(0);" onClick={me._using.bind(this, item)}>启用</a>
                                          &nbsp;&nbsp;&nbsp;&nbsp;
                                        <a href="Javascript:void(0);" onClick={me._unusing.bind(this, item)}>禁用</a>
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