import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import Axios from 'axios';
import {BASE_URL} from '../constants.js';
import getAuthHeader from "../GetHeader";
import HeadingSuccessAlert from "../components/HeadingSuccessAlert";
import HeadingAlert from "../components/HeadingAlert";
import { Redirect } from "react-router-dom";

class SuccessfulOpeningAccount extends Component {
    state = {
        store : "",
        hiddenSuccessAlert: true,
		successHeader: "",
		successMessage: "",
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",    
        minDate:new Date()
    }

  

    handleAdd = () => {
        let siloDTO = {
            purchasePlace : this.props.forPurchasePlace,
            name: this.state.name, 
            description:this.state.description,
            capacity: this.state.capacity, 
        };

        Axios
        .post(BASE_URL + "/api/purchase/addSilo", siloDTO, {
            headers: { Authorization: getAuthHeader() },
        }).then((res) =>{
            this.setState({
                hiddenSuccessAlert: false,
                hiddenFailAlert:true,
                successHeader: "Success",
                successMessage: "You added successfully silo for purchase place.",
            })

        }).catch((err) => {
            this.setState({ 
                hiddenSuccessAlert: true,
                hiddenFailAlert: false, 
                failHeader: "Unsuccess", 
                failMessage: "It is not possible to add silo"});
        });
    }

    handleClickOnClose = () => {
        this.props.onCloseModal();
        window.location.reload();

    }

    handleCloseAlertSuccess = () => {
		this.setState({ hiddenSuccessAlert: true });
    };
    
    handleCloseAlertFail = () => {
		this.setState({ hiddenFailAlert: true });
	};

    render() { 
        if (this.state.unauthorizedRedirect) return <Redirect push to="/unauthorized" />;

        return ( 
            <Modal
                show = {this.props.show}
                size = "md"
                dialogClassName="modal-80w-130h"
                aria-labelledby="contained-modal-title-vcenter"
                centered
               
                >
                <Modal.Body                     style={{backgroundColor:"#E8EDEE"}} 
>
                    <HeadingSuccessAlert
						hidden={this.state.hiddenSuccessAlert}
						header={this.state.successHeader}
						message={this.state.successMessage}
						handleCloseAlert={this.handleCloseAlertSuccess}
					/>
                    <HeadingAlert
                            hidden={this.state.hiddenFailAlert}
                            header={this.state.failHeader}
                            message={this.state.failMessage}
                            handleCloseAlert={this.handleCloseAlertFail}
                    />
                
                    <div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1}}>
                    <label style={{fontSize : "17px", fontWeight : "bold",color : "blue"}}>You have successfully opened accout, your card data is:</label>
  
                    <form id="contactForm" name="sentMessage" noValidate="novalidate">
							<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Cardholder name:</label>
										<input
                                        readonly
											class="form-control"
											type="text"
											id="name"
											value={this.props.cardHolderName}
										/>
									</div>
								
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Cardholder UCIN:</label>
										<input
                                        readonly
											class="form-control"
											type="text"
											id="surname"
											value={this.props.cardHolderUCIN}
										/>
									</div>
								
								</div>
                                <div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>PAN(Card numer):</label>
										<input
                                        readonly
											class="form-control"
											type="text"
											id="surname"
											value={this.props.pan}
										/>
									</div>
								
								</div>
                                <div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Card security code:</label>
										<input
                                        readonly
											class="form-control"
											type="text"
											id="surname"
											value={this.props.cardSecurityCode}
										/>
									</div>
								
								</div>
                                <div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Expiration date:</label>
										<input
                                        readonly
											class="form-control"
											type="date"
											id="surname"
											value={this.props.expirationDate}
										/>
									</div>
								
								</div>
								
								
								
								<div className="form-group" style={{alignItems : "right"}}>
									<button
									
										onClick={this.handleClickOnClose}
										className="btn btn-info btn-xl"
										id="sendMessageButton"
										type="button"
									>
										OK
									</button>
								</div>
							</form>

                      







									</div>
                </Modal.Body>
                
            </Modal>
         );
    }
}
 
export default SuccessfulOpeningAccount;