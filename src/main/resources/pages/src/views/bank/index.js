var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var FormItem = UcsmyUI.Form.FormItem;
var Grid = require('../widget/other/grid');
var Form = require('./other/form');
var SelectDropDown = UcsmyUI.SelectDropDown;

var option = [
    {option: '启用', value: '1'},
    {option: '禁用', value: '2'}
];

myPanel = React.createClass({

    getInitialState:function () {

        return{
            province:'',
            city:'',
            defProvince:''
        }
    },
    componentWillMount: function()
    {
        var me = this;
        $.ajax({
           url:'province/query',
            type: 'POST',
            async:false,
            dataType:'JSON',
            success:function(data)
            {
                if(data.retmsg != 'success')
                {
                    return;
                }
                me.setState({
                    province: data.data,
                    defProvince:data.data[0].value
                });
            }
        });
    },
    _provinceChange:function(event)
    {
        var me = this;
        if(null==event || ''===event)
        {
            me.setState({
                city: ''
            });
            return;
        }
        $.post("city/query", {'provinceNo':event}, function(data) {
            if(data.retmsg != 'success') {
                return;
            }
            me.setState({
                city: data.data
            });
        }, "json").error(function(){
            UcsmyIndex.alert("失败","系统异常");
        });

    },
    _reload: function () {
        this.refs.grid.reload();
    },
    _add: function () {
        var me = this;
        UcsmyIndex.openChildrenPage(Form, function (ref) {
            ref.init('bank/add', '添加支行', {}, function() {
            	me._reload();
            });
        });
    },
    _edit: function (item) {
        var me = this;
        UcsmyIndex.openChildrenPage(Form, function (ref) {
            ref.init('bank/update', '修改支行', item, function() {
            	me._reload();
            });
        });
    },
    _delete: function (item) {
        var me = this;
        UcsmyIndex.confirm("确定", "你真的要删除该数据吗？", function() {
        	$.post('bank/del', {id: item.id}, function (result) {
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
            provinceNo: this.refs.provinceNo.getValue(),
            cityNo: this.refs.cityNo.getValue(),
            nationsNo: this.refs.nationsNo.getValue(),
            status: this.refs.status.getValue(),
            bankName: this.refs.bankName.getValue(),
            bankDirect: this.refs.bankDirect.getValue(),
        });
    },
    render: function() {
        var me = this;
        return (
            <div>
                <h2 className="content-title">开户支行管理</h2>
                <div className="panel">
                    <div className="panel-title">查询条件</div>
                    <div className="panel-content">

                    <FormItem label="省">
                      <SelectDropDown ref="provinceNo" defaultValue ={this.state.defProvince} showNum="8" onChange={me._provinceChange} option={me.state.province} searchPlaceholder="请选择" />
                    </FormItem>
                    <FormItem label="市">
                    <SelectDropDown ref="cityNo" defaultText="请选择" defaultValue="" showNum="8"  option={me.state.city} searchPlaceholder="请选择" />
                    </FormItem>
                    <FormItem label="状态">
                    <SelectDropDown ref="status" defaultText="请选择" defaultValue="" option={option} searchPlaceholder="请选择" />
                    </FormItem>
                        <FormItem label="联行号"><Input ref="nationsNo"/></FormItem>
                        <FormItem label="支行名称"><Input ref="bankName"/></FormItem>
                        <FormItem label="所属行号"><Input ref="bankDirect"/></FormItem>
                    </div>
                </div>
                <div className="btn-panel">
                    <Button buttonType="bidnow" onClick={this._search}>查询</Button>
                    <Button buttonType="bidnow" onClick={this._add}>添加</Button>
                </div>

                <div className="table-panel">
                    <Grid url="bank/query" ref = "grid"
                          parameters={{"provinceNo":me.state.defProvince}}
                          isTextOverflowHidden={true}
                          columns={[
                            {name: 'provinceName', header: '省名称'},
                            {name: 'cityName', header: '市名称'},
                            {name: 'bankDirect', header: '所属行号'},
                            {name: 'nationsNo', header: '联行号'},
                            {name: 'bankName', header: '支行名称'},
                            {name: 'status', header: '状态', content:function(column){
                                return (<span>
                                {column.status === '1' ? '启用' : '禁用'}
                            </span>)
                            }},
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