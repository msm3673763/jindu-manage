var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Form = UcsmyUI.Form;
var FormItem = Form.FormItem;


var configFormData = {

    "name": [
        {type: "required", msg: "名称不能为空"},
        {type : "maxLength", maxLength : 100, msg : "参数名称长度不能超过100"}
    ],
    "description": [
        {type: "required", msg: "描述不能为空"},
        {type : "maxLength", maxLength : 100, msg : "参数描述长度不能超过100"}
    ],
    "file": [
        {type: "required", msg: "文件不能为空"}
    ]

};

module.exports = React.createClass({
    getInitialState: function () {
        return {
            url: '',
            title: '',
            callback: function(){},
            config: {}
        }
    },
    _validate: function (callback) {
        var status = false;
        var validateField = [
            "name", "description", "file"
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

            var data = new FormData();
            //如果需要上传多文件，可以允许
            $.each($('#file')[0].files, function(i, file) {
                data.append("file", file);
            });
            //添加所需要的参数
            data.append("name", $('#name').val());
            data.append("description", $('#description').val());
            //
            $.ajax({
                url: 'client/upload',
                data: data,
                cache: false,
                contentType: false,
                processData: false,
                type: 'POST',
                success: function(result){
                    if (result.retcode == "0") {
                        UcsmyIndex.alert("提示", result.retmsg);
                        UcsmyIndex.closeChildrenPage();
                        me.state.callback();
                    } else {
                        UcsmyIndex.alert("提示", result.retmsg);
                    }
                }
            });



        });
    },
    init: function (url, title, data, callback) {
        var me = this;
        this.setState({
            title: title,
            url: url,
            config: data,
            callback: callback
        });
        // this.refs.saveForm.setValues(data);
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
                            <input type="hidden" name="id" value={this.state.config.id}/>
                            <FormItem label="文件名称"><Input id="name" name="name"  value={this.state.config.paramKey}/></FormItem>
                            <FormItem label="文件描述"><Input id="description" name="description"  value={this.state.config.paramDesc}/></FormItem>
                            <FormItem label="文件"><Input id="file" name="file" type="file"/></FormItem>
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