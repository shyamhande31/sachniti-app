import axios from "axios";
import React, { Component, Fragment } from "react";
import { Form, Table, Modal } from "react-bootstrap";

class GenerateNotices extends Component {

    constructor() {
        super();
        this.state = {
            lotNumber: [],
            loanNumber: [],
            selectedLotNumber: "",
            show: false,
        }
    }

    componentDidMount() {
        axios.get("http://localhost:8080/getLotNumbers").then((res) => {
            this.setState({ lotNumber: [...res.data] });
        }).catch((error) => {
            console.log(error);
        })
    }

    handleChange = (value) => {
        this.setState({ selectedLotNumber: value })
        axios.get("http://localhost:8080/getAllLoanNumbers", {
            params: {
                lotNumber: value
            }
        }).then((res) => {
            this.setState({ loanNumber: [...res.data] });
        }).catch((error) => {
            console.log(error);
        })
    }

    generateNotices = () => {
        this.handleClose();
        axios.get("http://localhost:8080/generateNotices", {
            params: {
                lotNumber: this.state.selectedLotNumber
            }
        }).then((res) => {
            alert("Generated");
        }).catch((error) => {
            alert("Notices are not generated")
        })
    }

    handleShow = () => {
        this.setState({ show: true })
    }

    handleClose = () => {
        this.setState({ show: false })
    }

    render() {
        return (
            <Fragment>
                <h1 style={{ textAlign: "center" }}>Generate Notices</h1>
                <br />
                <form className="generateNoticeForm">
                    <div className="row">
                        <div style={{ width: "23%" }}>
                            <Form.Select onChange={(event) => this.handleChange(event.target.value)}>
                                <option>--Select Lot Number--</option>
                                {this.state.lotNumber.length > 0 ?
                                    this.state.lotNumber.map((value) =>
                                        <option>{value}</option>
                                    ) : null}
                            </Form.Select>
                        </div>
                    </div>
                </form><br />
                <div className="generateNoticeTable">
                    <div className="detailsTable">
                        <Table striped variant="light">
                            <thead>
                                <tr>
                                    <th>S.N.</th>
                                    <th>Reference Number</th>
                                    <th>Customer Name</th>
                                    <th>Loan Number</th>
                                </tr>
                            </thead>
                            <tbody>
                                {this.state.loanNumber.map((value, index) =>
                                    <tr>
                                        <td>{index + 1}</td>
                                        <td>{value.referenceNumber}</td>
                                        <td>{value.customerName}</td>
                                        <td>{value.loanNumber}</td>
                                    </tr>
                                )}
                            </tbody>
                        </Table>
                    </div>
                    <br />
                    <div className="generateNoticesButtons">
                        <button className="btn btn-primary" onClick={() => this.handleShow()}>Generate</button>&nbsp;
                        <button className="btn btn-danger">Cancel</button>
                    </div>
                </div>

                <Modal show={this.state.show} onHide={this.handleClose}>
                    <Modal.Body>
                        <h4>Do you want to generate notices for all customers?</h4>
                        <button className="btn btn-success" onClick={()=>this.generateNotices()}>Yes</button>&nbsp;
                        <button className="btn btn-danger" onClick={()=>this.handleClose()}>No</button>
                    </Modal.Body>
                </Modal>
            </Fragment>
        )
    }
}

export default GenerateNotices;