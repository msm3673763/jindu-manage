var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Form = UcsmyUI.Form;
var FormItem = Form.FormItem;
var Radio = UcsmyUI.Radio;
var RadioGroup = Radio.RadioGroup;

var configFormData = {

    "applyName": [
        {type: "required", msg: "应用名称不能为空"},
        {type : "maxLength", maxLength : 64, msg : "应用名称长度不能超过64"}
    ],
    "applyUrl": [
        {type: "required", msg: "应用的链接不能为空"},
        {type : "maxLength", maxLength : 256, msg : "应用的链接长度不能超过256"}
    ],
    "applyDes": [
        {type : "maxLength", maxLength : 128, msg : "应用描述长度不能超过128"}
    ],
    "imgEnter": [
        {type: "required", msg: "图片不能为空"}
    ],
    "imgMove": [
        {type: "required", msg: "图片不能为空"}
    ]
};

function readURL(input, name) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();


        reader.onload = function (e) {
            var img = $('#' + name);
            img.attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);

    } else {
        $('#' + name).attr('src', '');
    }
}



module.exports = React.createClass({
    getInitialState: function () {
        return {
            url: '',
            title: '',
            callback: function(){},
            apply: {}
        }
    },
    componentDidMount: function() {

        $("#imgEnter").change(function(){
            readURL(this, "imgEnterDisplay");
        });
        $("#imgMove").change(function(){
            readURL(this, "imgMoveDisplay");
        });
    },
    _validate: function (callback) {
        var status = false;
        if (this.state.apply.imgEnter && this.state.apply.imgMove) {
            var validateField = [
                "applyName", "applyUrl", 'applyDes'
            ];
        } else {
            var validateField = [
                "applyName", "applyUrl", 'applyDes', "imgEnter", "imgMove"
            ];
        }
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
            $.each($('#imgEnter')[0].files, function(i, file) {
                data.append("entFile", file);
            });
            $.each($('#imgMove')[0].files, function(i, file) {
                data.append("movFile", file);
            });
            data.append("uuid", me.state.apply.uuid);
            data.append("applyName",me.refs.applyName.getValue());
            data.append("applyUrl",me.refs.applyUrl.getValue());
            data.append("applyDes",me.refs.applyDes.getValue());
            data.append("status",me.refs.status.getValue());

            $.ajax({
                url: me.state.url,
                data: data,
                cache: false,
                contentType: false,
                processData: false,
                type: 'POST',
                success: function(result){
                    _removeButtonDisabled('save');
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
        this.setState({
            title: title,
            url: url,
            apply: data,
            callback: callback,
        });


        if(data.imgEnter) {
            $('#imgEnterDisplay').attr('src', "application/getImage/" + data.imgEnter);
        }
        if(data.imgMove) {
            $('#imgMoveDisplay').attr('src', "application/getImage/" + data.imgMove);
        }
    },
    _return: function () {
        UcsmyIndex.closeChildrenPage();
    },
    render: function () {
        var me = this;

        return (
            <div>
                <div className="panel">
                    <div className="panel-title fc-red">{this.state.title}</div>
                    <div className="panel-content">
                        <Form ref="saveForm" formData={configFormData} id="saveForm">
                            <input type="hidden" name="uuid" value={this.state.apply.uuid}/>
                            <FormItem label="应用名称"><Input ref="applyName" name="applyName"  value={this.state.apply.applyName}/></FormItem>
                            <FormItem label="应用链接"><Input ref="applyUrl" name="applyUrl"  value={this.state.apply.applyUrl}/></FormItem>
                            <FormItem label="应用描述"><Input ref="applyDes" name="applyDes"  value={this.state.apply.applyDes}/></FormItem>
                            <FormItem label="是否展示" className="col-xs-5">
                                <RadioGroup ref="status" name="status" value={this.state.apply.status == null ? "1" : this.state.apply.status}>
                                <Radio value="1"><span style={{"padding-left": "20px"}}>是</span></Radio>
                                <Radio value="0"><span style={{"padding-left": "20px"}}>否</span></Radio>
                            </RadioGroup>
                            </FormItem>
                            <FormItem label="鼠标移入图片"><Input id="imgEnter" name="imgEnter" type="file" ref="imgEnter" onChange={me._onChange}/></FormItem>
                            <FormItem label="鼠标移出图片"><Input id="imgMove" name="imgMove" type="file"/></FormItem>
                        </Form>
                        <br/>
                        <img  id="imgEnterDisplay" width="250" />
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <img  id="imgMoveDisplay" width="250"/>
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