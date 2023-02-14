import React, { Component, Fragment } from "react";
import { BiCloudUpload } from "react-icons/bi";
import axios from "axios";
import { Form } from "react-bootstrap";

class TemplateUpload extends Component {

    constructor(){
        super();
        this.state={
            form:{
                bankName:'',
                productName:'',
                language:'',
                selectTemplateLocation:'',
            },
            banks:["ICICI","HDFC","SBI","CBI"],
            products:["AUTO","CAR","HOME"],
            language:["Hindi","English","Marathi","Bengali"],
        }
    }

    handleChange=(event)=>{
        let formDum=this.state.form;
        if(event.target.name==='selectTemplateLocation'){
            formDum[event.target.name]=event.target.files[0];
        }else{
            let { name, value } = event.target;
            formDum[name] = value;   
        }
        this.setState({ form: formDum });
    }

    handleSubmit=(event)=>{
        event.preventDefault();
        let formData=new FormData();
        formData.append('file',this.state.form.selectTemplateLocation)
        formData.append('extra',this.state.form.bankName+"_"+this.state.form.productName+"_"+this.state.form.language)
        axios.post("http://localhost:8080/templateUpload", formData).then((res) => {
             alert("Template Uploaded Successfully")
        }).catch(err => {
            console.log(err.message)
        })
        Array.from(document.querySelectorAll("input")).forEach(
            input => (input.value = "")
        );
        Array.from(document.querySelectorAll("Select")).forEach(
            Select => (Select.value = "--select--")
        );
        this.setState({ form: [{}] });
    }

    render() {
        return (
            <Fragment>
                <h2 style={{ textAlign: "center" }}>Template Upload</h2><br />
                <div className="card applicationUploadCard">
                    <div className="card-body">
                        <h2>Template Upload <BiCloudUpload/></h2><br />
                        <form onSubmit={this.handleSubmit}>
                            <div className="row">
                                <div style={{ width: "70%" }}>
                                    <div className="row">
                                        <div className="col form-group">
                                            <label className="labelText">Bank Name</label>
                                            <Form.Select id="bankName" name="bankName" onChange={this.handleChange}>
                                                <option>--select--</option>
                                                {this.state.banks.map((value)=>(
                                                    <option>{value}</option>
                                                ))}
                                            </Form.Select>
                                        </div>
                                        <div className="col form-group">
                                            <label className="labelText">Product Name</label>
                                            <Form.Select id="productName" name="productName" onChange={this.handleChange}>
                                                <option>--select--</option>
                                                {this.state.products.map((value)=>(
                                                    <option>{value}</option>
                                                ))}
                                            </Form.Select>
                                        </div>
                                        <div className="col form-group">
                                            <label className="labelText">Language</label>
                                            <Form.Select id="language" name="language" onChange={this.handleChange}>
                                                <option>--select--</option>
                                                {this.state.language.map((value)=>(
                                                    <option>{value}</option>
                                                ))}
                                            </Form.Select>
                                        </div>
                                    </div><br/>
                                    <div className="form-group">
                                        <label  className="labelText">Select Template Location</label>
                                        <input type="file" name="selectTemplateLocation" id="selectTemplateLocation" className="form-control" onChange={this.handleChange} /><br />
                                    </div><br/>
                                    <button className="btn btn-primary" style={{ float: "right" }}>Upload</button>
                                </div>
                                <div className="col" style={{ width: "30%" }}>
                                    <br/><br/><br/><br/><br/><br/><br/><br/>
                                    <button className="btn btn-danger">Cancel</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </Fragment>
        )
    }
}

export default TemplateUpload;