import React, { Component, Fragment } from "react";
import { Form } from "react-bootstrap";
import { BiCloudUpload } from "react-icons/bi";
import axios from "axios";
import { connect } from 'react-redux';

class ApplicationUpload extends Component {

    constructor() {
        super();
        this.state = {
            form: {
                lotNumber: '',
                applicaionFIle: '',
                templateUsed: '',
            },
            availableTemplate: [],
            uploadedLotNumber: '',
        }
    }

    componentDidMount() {
        axios.get("http://localhost:8080/getAllTemplateName").then((res) => {
            this.setState({ availableTemplate: [...res.data] });
        }).catch((error) => {
            console.log(error);
        })
    }

    handleChange = (event) => {
        let formDum = this.state.form;
        if (event.target.name === 'applicaionFIle') {
            formDum[event.target.name] = event.target.files[0];
        } else {
            let { name, value } = event.target;
            formDum[name] = value;
        }
        this.setState({ form: formDum });
    }

    handleSubmit = (event) => {
        event.preventDefault();
        console.log(this.state.form)
        let formData = new FormData();
        formData.append('file', this.state.form.applicaionFIle)
        formData.append('extra', this.state.form.templateUsed)
        axios.post("http://localhost:8080/applicationUpload", formData).then((res) => {
            this.setState({ uploadedLotNumber: res.data })
        }).catch(err => {
            console.log(err.message)
        })
        Array.from(document.querySelectorAll("input")).forEach(
            input => (input.value = "")
        );
        this.setState({ form: [{}] });
    }


    render() {
        return (
            <Fragment>
                <h2 style={{ textAlign: "center" }}>Application Upload</h2><br />
                <div className="card applicationUploadCard">
                    <div className="card-body">
                        {this.state.uploadedLotNumber !== '' ? <h4 className="text-success">Application uploaded successfully with lot number "{this.state.uploadedLotNumber}"</h4> : null}
                        <h2>File Upload <BiCloudUpload /></h2><br />
                        <form onSubmit={this.handleSubmit}>
                            <div className="row">
                                <div style={{ width: "70%" }}>
                                    <div className="form-group">
                                        <label className="labelText">Lot Number</label>
                                        <input type="text" disabled name="lotNumber" id="lotNumber" className="form-control" placeholder="Lot Number" onChange={this.handleChange} /><br />
                                    </div>
                                    <div className="form-group">
                                        <label className="labelText">File Name</label>
                                        <input type="file" name="applicaionFIle" id="applicationFile" className="form-control" onChange={this.handleChange} /><br />
                                    </div>
                                    <div className="form-group">
                                        <label className="labelText">Select template to be used</label>
                                        <Form.Select type="text" name="templateUsed" id="templateUsed" className="form-control" onChange={this.handleChange}>
                                            <option>Select Template to be used</option>
                                            {this.state.availableTemplate.length > 0 ?
                                                this.state.availableTemplate.map((value) =>
                                                    <option>{value}</option>
                                                ) : null}
                                        </Form.Select>
                                    </div><br />
                                    <button className="btn btn-primary" style={{ float: "right", marginTop: "1%" }}>Upload</button>
                                </div>
                                <div className="col" style={{ width: "30%" }}>
                                    <br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
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

function mapStateToProps(value){
    let {token} =value;
    return {token};
  }
  
export default connect(mapStateToProps)(ApplicationUpload);