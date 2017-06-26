var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var FormItem = UcsmyUI.Form.FormItem;
var Grid = require('../widget/other/grid');
var Form = require('./other/form');
var SelectDropDown = UcsmyUI.SelectDropDown;
myPanel = React.createClass({
    getInitialState:function () {

        return{
            paramTypes:'',
        }
    },
    _reload: function () {
        this.refs.grid.reload();
    },
    _add: function () {
        var me = this;
        UcsmyIndex.openChildrenPage(Form, function (ref) {
            ref.init('parameter/addParameter', '添加业务类型', {}, function() {
            	me._reload();
            });
        });
    },
    componentDidMount:function()
    {
        var me = this;
        $.post('parameter/getparamTypes',null,function(result)
        {
           me.setState({
               paramTypes:result.data
           });
        });
    },
    _edit: function (item) {
        var me = this;
        UcsmyIndex.openChildrenPage(Form, function (ref) {
            ref.init('parameter/updateParameter', '修改业务类型', item, function() {
            	me._reload();
            });
        });
    },
    _delete: function (item) {
        var me = this;
        UcsmyIndex.confirm("确定", "你真的要删除该数据吗？", function() {
        	$.post('parameter/delParameter', {id: item.id}, function (result) {
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
            paramValue: this.refs.paramValue.getValue(),
            paramType:this.refs.paramType.getValue()
        });
    },
    render: function() {
        var me = this;

        return (
            <div>
                <h2 className="content-title">业务类型管理</h2>
                <div className="panel">
                    <div className="panel-title">查询条件</div>
                    <div className="panel-content">
                        <FormItem label="参数名称"><Input ref="paramValue"/></FormItem>
                        <FormItem label="参数类型">
                            <SelectDropDown ref="paramType" defaultText="请选择" defaultValue="" showNum="8" option={me.state.paramTypes} searchPlaceholder="请选择" />
                        </FormItem>
                    </div>
                </div>
                <div className="btn-panel">
                    <Button buttonType="bidnow" onClick={this._search}>查询</Button>
                    <Button buttonType="bidnow" onClick={this._add}>添加</Button>
                </div>

                <div className="table-panel">
                    <Grid url="parameter/queryParameterList" ref = "grid"
                          isTextOverflowHidden={true}
                          isTextOverflowHidden={true}
                          retDataProperty='data.resultList'
                          retTotalProperty='data.totalCount'
                          retCurrentProperty='data.pageNo'
                          columns={[
                            {name: 'paramName', header: '参数类型'},
                            {name: 'key', header: '参数代码'},
                            {name: 'value', header: '参数名称'},
                            {name: 'priority', header: '优先级', width: 300},
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