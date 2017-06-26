var Button = UcsmyUI.Button;
var Form = UcsmyUI.Form;
var FormItem = Form.FormItem;
var Textarea = UcsmyUI.Textarea;
var Input = UcsmyUI.Input;


module.exports = React.createClass({
    getInitialState: function () {
        return {
            url: '',
            title: '',
            callback: function(){},
            item: {}
        }
    },
    init: function (url, title, data, callback) {
        this.setState({
            title: title,
            url: url,
            item: data,
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
                    <div className="panel-content template-panel">
                        <FormItem label="工单号"><Input value={this.state.item.orderNumber} disabled="false"/></FormItem>
                        <FormItem label="提交时间"><Input value={formatDate(this.state.item.createDate)} disabled="true"/></FormItem>
                        <FormItem label="提交人"><Input  value={this.state.item.createUser} disabled="true"/></FormItem>
                        <FormItem label="联系电话"><Input value={this.state.item.contact} disabled="true"/></FormItem>
                        <FormItem label="处理人"><Input value={this.state.item.processUser} disabled="true"/></FormItem>
                        <FormItem label="处理时间"><Input value={formatDate(this.state.item.processDate)} disabled="true"/></FormItem>
                        <FormItem label="反馈意见" className="template-content-div">
                            <Textarea name="content"  value={this.state.item.message} className="template-content" disabled="true" />
                        </FormItem>
                    </div>
                </div>
                <div className="btn-panel">
                    <Button buttonType="cancel" onClick={this._return}>返回</Button>
                </div>
            </div>
        )
    }
});