var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Form = UcsmyUI.Form;
var FormItem = Form.FormItem;


var configFormData = {

    "name": [
        {type: "required", msg: "邮箱名称不能为空"},
        {type : "maxLength", maxLength : 64, msg : "邮箱名称长度不能超过64"}
    ],
    "domainName": [
        {type: "required", msg: "域名不能为空"},
        {type : "maxLength", maxLength : 64, msg : "参数值长度不能超过64"},
        {type: "fn", validator: function(value){
           var d = /^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$/;
            return d.test(value);
        }, msg: '域名格式不正确'
        }
    ],
    "domainUrl": [
        {type: "required", msg: "登录首页不能为空"},
        {type : "maxLength", maxLength : 256, msg : "登录首页长度不能超过256"},
        {type: "fn", validator: function(value){
            var reg=/(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;
            return reg.test(value)
        }, msg: '登录首页不是正确的网址'
        }
    ],
    "des": [
        {type : "maxLength", maxLength : 256, msg : "描述长度不能超过256"}
    ]
};

module.exports = React.createClass({
    getInitialState: function () {
        return {
            url: '',
            title: '',
            callback: function(){},
            email: {}
        }
    },
    _validate: function (callback) {
        var status = false;
        var validateField = [
            "name", "domainName", "domainUrl",'des'
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
        var me = this;
        this.setState({
            title: title,
            url: url,
            email: data,
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
                            <input type="hidden" name="uuid" value={this.state.email.uuid}/>
                            <FormItem label="邮箱名称"><Input name="name"  value={this.state.email.name}/></FormItem>
                            <FormItem label="邮箱域名"><Input name="domainName"  value={this.state.email.domainName}/></FormItem>
                            <FormItem label="登录首页"><Input name="domainUrl"  value={this.state.email.domainUrl}/></FormItem>
                            <FormItem label="描述"><Input name="des"  value={this.state.email.des}/></FormItem>
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