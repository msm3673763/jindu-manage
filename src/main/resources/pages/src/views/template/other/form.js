var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Form = UcsmyUI.Form;
var FormItem = Form.FormItem;
var SelectDropDown = UcsmyUI.SelectDropDown;
var Textarea = UcsmyUI.Textarea;

var title_required = {type: "required", msg: "模板主题不能为空"};
var title_max = {type: "maxLength", maxLength: 100, msg: "模板主题长度不能超过100"};
module.exports = React.createClass({
    getInitialState: function () {
        return {
            url: '',
            title: '',
            callback: function(){},
            template: {},
            type:[],
            formData: {
                "title":[title_max],
                "content": [
                    {type: "required", msg: "模板内容不能为空"},
                    {type: "maxLength", maxLength: 1000, msg: "模板内容长度不能超过1000"}
                ],
                "type": [
                    {type: "required", msg: "模板类型必须选择"}
                ],
                "description": [
                    {type: "maxLength", maxLength: 1000, msg: "描述长度不能超过1000"}
                ]
            }
        }
    },
    _validate: function(callback) {
        var status = false;
        var validateField = [
           "title", "content", "type","description"
        ];
        var fn = function (result){
            if(result) {
                callback();
            }
        };
        this.refs.saveForm.validate(fn, validateField);

        return status;
    },
    componentDidUpdate: function() {
        if(this.state.template.typeKey){
            var title = [];
            title.push(title_max);
            var val = this.state.template.typeKey;
            if(val.indexOf("email") == 0){
                title.push(title_required);
            }
            var formData = this.state.formData;
            formData['title'] = title;
        }
    },
    _selectChange : function(val){
        var title = [];
        title.push(title_max);
        if(val.indexOf("email") == 0){
            title.push(title_required);
        }
        var formData = this.state.formData;
        formData['title'] = title;
        this._validate(function(){});
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
        var me = this;
        var type = [];
        $.post("parameter/queryParameter",
            {type:'template_type'},
            function (result) {
                if (result.retcode == "0") {
                    var data = result.data;
                    if(data != null) {
                        for (var i = 0; i < data.length; i++) {
                            type.push({
                                option: data[i].value,
                                value: data[i].key
                            });
                        }
                        me.setState({
                            type : type
                        });
                    }
                } else {

                }
            }, "json").error(function(xhr, errorText, errorType){

        });
        me.setState({
            title: title,
            url: url,
            template: data,
            callback: callback
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
                        <Form ref="saveForm" formData={this.state.formData} id="saveForm">
                            <input type="hidden" name="id" value={this.state.template.id}/>
                            <FormItem label="模板主题"><Input name="title"  value={this.state.template.title}/></FormItem>
                            <FormItem label="模板类型">
                                <SelectDropDown defaultText="请选择" defaultValue="" onChange={this._selectChange}
                                                option={this.state.type} className="setwidth"
                                                value={this.state.template.typeKey} ref="type" name="type" />
                            </FormItem>
                            <FormItem label="模板描述"><Input name="description"  value={this.state.template.description}/></FormItem>
                            <FormItem label="模板内容" className="template-content-div">
                                <Textarea name="content"  value={this.state.template.content} className="template-content" />
                            </FormItem>
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