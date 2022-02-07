import React, { Component } from "react";
import { Link } from "react-router-dom";
import Axios from "axios";
import { BASE_URL } from "../constants.js";
import { Button } from "react-bootstrap";
import { Redirect } from "react-router-dom";
import { dateToStringFormat } from "igniteui-react-core";
import PasswordMask from 'react-password-mask';



class MerchantRegistration extends Component {
	state = {
		banks: [],
		redirect: false,
		redirectUrl : "",
        pan : "",
        cardHolderName : "",
        cardSecurityCode : "",
        expirationDate : "",
        showForm : true,
        showMerchant : false,
        merchantId : "",
        merchantPassword : ""

	};
	hasRole = (reqRole) => {
		let roles = JSON.parse(localStorage.getItem("keyRole"));

		if (roles === null) return false;

		if (reqRole === "*") return true;

		for (let role of roles) {
			if (role === reqRole) return true;
		}
		return false;
	};
	

	handleClickOnBank= (id) => {
		this.setState({
			redirect: true,
			redirectUrl: "/bank/" + id,
		});
	};
	componentDidMount() {
		
		Axios.get(BASE_URL + "/api/bank/all")
			.then((res) => {
				this.setState({ banks: res.data });
			})
			.catch((err) => {
				console.log(err);
			});

	}
    handlePANChange = (event) => {
		this.setState({ pan: event.target.value });
	};
    handleCardHolderNameChange = (event) => {
		this.setState({ cardHolderName: event.target.value });
	};
    handleCardSecurityCodeChange = (event) => {
		this.setState({ cardSecurityCode: event.target.value });
	};
    handleExpirationDateChange = (event) => {
		this.setState({ expirationDate: event.target.value });
	};

    handleRegister = ()=>{
        let dto = {
            cardHolderName: this.state.cardHolderName,
            cardSecurityCode: this.state.cardSecurityCode,
            pan: this.state.pan,
            expirationDate: this.state.expirationDate,
        };
        
                Axios.post(BASE_URL + "/api/bank/registerMerchant", dto, { validateStatus: () => true })
                    .then((res) => {
                        if (res.status === 409) {
                            this.setState({
                                errorHeader: "Resource conflict!",
                                hiddenErrorAlert: false,
                            });
                        } else if (res.status === 500) {
                            this.setState({ errorHeader: "Internal server error!", errorMessage: "Server error.", hiddenErrorAlert: false });
                        }else if(res.status === 400){
								alert("You entered invalid data, please try again!")
						}
						 else {
                            console.log("Success");
                            this.setState({merchantPassword : res.data.merchantPassword})
                            this.setState({merchantId : res.data.merchantId})
                            this.setState({ showMerchant: true });
                            this.setState({showForm:false})

                        }
                    })
                    .catch((err) => {
                        console.log(err);
                    });
            
        
    }
    handleOk= ()=>{

    }
	render() {
        if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;

		return (
            <React.Fragment>

<div >
        <div className="container d-flex align-items-center" style={{ marginTop: "10%" }}>
					<div className="row section-design" style={{  margin: "auto",
  width: "50%",
  border: "2px solid blue",
  padding: "10px",
  textAlign: "center",
  backgroundColor:"#E8EDEE"
 }}>
						<div style={{display: "block",
  marginLeft: "auto",
  marginRight: "auto",
  width: "70%"}}>
							<br />
							<form hidden={!this.state.showForm} id="contactForm" name="sentMessage" noValidate="novalidate" style={{width : "100%"}}>
							<div className="control-group">
									<div className="form-group controls mb-0" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Card number(PAN):*</label>
																<PasswordMask
																	id="pan"
																	name="pan"
																	value={this.state.pan}
																	onChange={this.handlePANChange}
																	/>
									</div>
								
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Card Holder name:*</label>
										<input
											class="form-control"
											type="text"
											id="surname"
											onChange={this.handleCardHolderNameChange}
											value={this.state.cardHolderName}
										/>
									</div>
								
								</div>
								
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Card security code:*</label>
										<input
											class="form-control"
											id="phone"
											type="text"
											onChange={this.handleCardSecurityCodeChange}
											value={this.state.cardSecurityCode}
										/>
									</div>
								
								</div>
								<div className="control-group">
									<label>Expiration date:*</label>
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<input
											class="form-control"
											type="month"
											onChange={this.handleExpirationDateChange}
											value={this.state.expirationDate}
										/>
									</div>
									
								</div>
								
								<div className="form-group">
									<button
									
										onClick={this.handleRegister}
										className="btn btn-info btn-xl"
										id="sendMessageButton"
										type="button"
									>
										Register
									</button>
								</div>
							</form>



                            <form hidden = {!this.state.showMerchant} id="contactForm" name="sentMessage" noValidate="novalidate">
							<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Merchant ID:</label>
										<input
                                        readonly
											class="form-control"
											type="text"
											id="name"
											onChange={this.handlePANChange}
											value={this.state.merchantId}
										/>
									</div>
								
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Merchant password:</label>
										<input
                                        readonly
											class="form-control"
											type="text"
											id="surname"
											onChange={this.handleCardHolderNameChange}
											value={this.state.merchantPassword}
										/>
									</div>
								
								</div>
								
								
								
								<div className="form-group">
									<button
									
										onClick={this.handleOk}
										className="btn btn-info btn-xl"
										id="sendMessageButton"
										type="button"
									>
										OK
									</button>
								</div>
							</form>


						</div>
                        </div>

        </div>
      

        
    </div>
	
            </React.Fragment>

		);
	}
}

export default MerchantRegistration;
