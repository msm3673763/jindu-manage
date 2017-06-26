var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Form = UcsmyUI.Form;
var FormItem = Form.FormItem;
var SelectDropDown = UcsmyUI.SelectDropDown;

var configFormData = {

    "key": [
        {type: "required", msg: "参数代码不能为空"},
        {type : "maxLength", maxLength : 50, msg : "参数代码长度不能超过50"},
        {type: "fn", validator: function(value){
            var d = /^[A-Za-z0-9_-]*$/g;
            return d.test(value);
        }, msg: '参数代码只能为字母数字下划线'
        }
    ],
    "value": [
        {type: "required", msg: "参数名称不能为空"},
        {type : "maxLength", maxLength : 100, msg : "参数名称长度不能超过100"}
    ],
    "priority": [
        {type: "required", msg: "优先级不为能空"},
        {type:"maxLength", maxLength:9, msg : "优先级长度不能超过9"},
        {type: "fn", validator: function(value){
            var reg =/^[1-9]*[1-9][0-9]*$/;
            return reg.test(value)
        }, msg: '优先级只能为正整数'
        }
    ],
    "type": [
        {type: "required", msg: "参数类型不为能空"}
    ]
};

module.exports = React.createClass({
    getInitialState: function () {
        return {
            url: '',
            title: '',
            callback: function(){},
            parameter: {}
        }
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
    _validate: function (callback) {
        var status = false;
        var validateField = [
            "key", "value", "priority",'type'
        ];
        var fn = function (result) {
            if(result) {
                callback();
            }
        };

        this.refs.saveForm.validate(fn, validateField);

        return status;
    },
    _saveOrUpdate: function () {

        var me = this;
        this._validate(function(){
            _addButtonDisabled('save');
            $.post(me.state.url,
                $('#saveForm').serialize(),
                function (result) {
                _removeButtonDisabled('save');
                if (result.retcode == "0") {
                    UcsmyIndex.alert("提示", result.retmsg);
                    UcsmyIndex.closeChildrenPage();
                    me.state.callback();
                } else {
                    UcsmyIndex.alert("提示", result.retmsg);
                }
            }, "json").error(function(xhr, errorText, errorType){
                _removeButtonDisabled('save');
                UcsmyIndex.alert("失败", "网络异常");
            });
        });
    },
    init: function (url, title, data, callback) {
        this.setState({
            title: title,
            url: url,
            parameter: data,
            callback: callback,
        });
    },
    _return: function (event) {
        UcsmyIndex.closeChildrenPage();
    },
    render: function () {
        return (
            <div>
                <div className="panel">
                    <div className="panel-title fc-red">{this.state.title}</div>
                    <div className="panel-content">
                        <Form ref="saveForm" formData={configFormData} id="saveForm">
                            <input type="hidden" name="id" value={this.state.parameter.id}/>
                            <FormItem label="参数类型">
                                <SelectDropDown ref="type" name="type" defaultText="请选择" defaultValue="" showNum="8" option={this.state.paramTypes} value ={this.state.parameter.type} />
                            </FormItem>
                            <FormItem label="参数代码"><Input name="key"  value={this.state.parameter.key}/></FormItem>
                            <FormItem label="参数名称"><Input name="value"  value={this.state.parameter.value}/></FormItem>
                            <FormItem label="优先级"><Input name="priority"  value={this.state.parameter.priority}/></FormItem>
                        </Form>
                    </div>
                </div>
                <div className="btn-panel">
                    <Button id="save" buttonType="bidnow" onClick={this._saveOrUpdate}>保存</Button>
                    <Button buttonType="cancel" onClick={this._return}>取消</Button>
                </div>
            </div>
        )
    }
});