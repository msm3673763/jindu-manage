var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Form = UcsmyUI.Form;
var FormItem = Form.FormItem;
var Radio = UcsmyUI.Radio;
var RadioGroup = Radio.RadioGroup;
var SelectDropDown = UcsmyUI.SelectDropDown;
var configFormData = {

    "nationsNo": [
        {type: "required", msg: "联行号不能为空"},
        {type : "maxLength", maxLength : 32, msg : "联行号长度不能超过32"},
        {type: "fn", validator: function(value){
            var reg = /^[0-9]*$/;

            return reg.test(value)
        }, msg: '联行号只能为数字'
        }
    ],
    "bankType": [
        {type: "required", msg: "行别代码不能为空"},
        {type : "maxLength", maxLength : 32, msg : "行别代码长度不能超过32"},
        {type: "fn", validator: function(value){
            var reg = /^[0-9]*$/;

            return reg.test(value)
        }, msg: '行别代码只能为数字'
        }
    ],
    "bankDirect": [
        {type: "required", msg: "所属行号不能为空"},
        {type : "maxLength", maxLength : 32, msg : "所属行号长度不能超过32"},
        {type: "fn", validator: function(value){
            var reg = /^[0-9]*$/;

            return reg.test(value)
        }, msg: '所属行号只能为数字'
        }
    ],
    "nodeNo": [
        {type : "maxLength", maxLength : 32, msg : "节点代码长度不能超过32"},
        {type: "fn", validator: function(value){
            var reg = /^[0-9]*$/;
            return reg.test(value)
        }, msg: '节点代码只能为数字'
        }
    ],
    "bankName": [
        {type: "required", msg: "支行名称不能为空"},
        {type : "maxLength", maxLength : 128, msg : "支行名称长度不能超过128"}
    ],
    "area": [
        {type : "maxLength", maxLength : 128, msg : "地址长度不能超过128"}
    ],
    "postcode": [
        {type : "maxLength", maxLength : 16, msg : "邮编长度不能超过16"}
    ],
    "phone": [
        {type : "maxLength", maxLength : 32, msg : "电话长度不能超过32"}
    ],
    "cityNo": [
        {type: "required", msg: "城市必须选择"},
        {type: "fn", validator: function(value){
            if(null==value ||""===value) {
                return false;
            }else return true;
        }, msg: '城市必须选择'
        }
    ]
};

module.exports = React.createClass({
    getInitialState: function () {
        return {
            url: '',
            title: '',
            callback: function(){},
            bank: {},
            province:'',
            city:'',
            cityNo:''
        }
    },
    componentDidMount: function() {
        var me = this;
        $.post("province/query", {}, function(data) {
            if(data.retmsg != 'success') {
                return;
            }
            me.setState({
                province: data.data
            });
        }, "json").error(function(){
            UcsmyIndex.alert("失败","系统异常");
        });
    },
    _provinceChange:function(event)
    {
        var me = this;
        if(null==event || ''===event)
        {
            me.setState({
                city: null,
                cityNo:null
            });
            return;
        }
        me._setCity(event);
    },
    _setCity:function(event)
    {
        if(null==event || ''==event)
            return ;
        var me = this;
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
    _validate: function (callback) {
        var status = false;
        var validateField = [
            'nationsNo','bankType','bankDirect','nodeNo','bankName','area','postcode','phone','cityNo'
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
            }, "json").error(function(){
                _removeButtonDisabled('save');
                UcsmyIndex.alert("失败", "网络异常");
            });
        });
    },
    init: function (url, title, data, callback) {
        if(url=='bank/update')
        {
            this._setCity(data.provinceNo);
        }

        this.setState({
            title: title,
            url: url,
            bank: data,
            cityNo:data.cityNo,
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
                            <input type="hidden" name="id" value={this.state.bank.id}/>
                            <FormItem label="联行号"><Input name="nationsNo"  value={this.state.bank.nationsNo}/></FormItem>
                            <FormItem label="行别代码"><Input name="bankType"  value={this.state.bank.bankType}/></FormItem>
                            <FormItem label="所属行号"><Input name="bankDirect"  value={this.state.bank.bankDirect}/></FormItem>
                            <FormItem label="节点代码"><Input name="nodeNo"  value={this.state.bank.nodeNo}/></FormItem>
                            <FormItem label="支行名称"><Input name="bankName"  value={this.state.bank.bankName}/></FormItem>
                            <FormItem label="地址"><Input name="area"  value={this.state.bank.area}/></FormItem>
                            <FormItem label="邮编"><Input name="postcode"  value={this.state.bank.postcode}/></FormItem>
                            <FormItem label="电话"><Input name="phone"  value={this.state.bank.phone}/></FormItem>
                            <FormItem label="状态" className="col-xs-5">
                                <RadioGroup name="status" value={this.state.bank.status == null ? "1" : this.state.bank.status}>
                                    <Radio value="1"><span style={{"padding-left": "20px"}}>启用</span></Radio>
                                    <Radio value="2"><span style={{"padding-left": "20px"}}>禁用</span></Radio>
                               </RadioGroup>
                            </FormItem>
                            <FormItem label="省" >
                               <SelectDropDown name="provinceNo" option={this.state.province} defaultText="请选择" defaultValue="" value ={this.state.bank.provinceNo} showNum="8"  onChange={this._provinceChange} className="setwidth" ref="urgentFlag" />
                            </FormItem>

                        <FormItem label="市" >
                          <SelectDropDown name="cityNo" option={this.state.city} defaultText="请选择" defaultValue=""  value ={this.state.cityNo} showNum="8"  className="setwidth" ref="urgentFlag" />
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