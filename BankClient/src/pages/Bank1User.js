import React, { Component } from "react";
import { Link } from "react-router-dom";
import Axios from "axios";
import { BASE_URL } from "../constants.js";
import { Button } from "react-bootstrap";
import { Redirect } from "react-router-dom";
import { dateToStringFormat } from "igniteui-react-core";
import TopBar from "../components/TopBar";



class Bank1User extends Component {
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
        merchantPassword : "",
        paymentId: "",
        paymentUrl: "",
		

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
    getCookie = (cname) => {
        let name = cname + "=";
        let decodedCookie = decodeURIComponent(document.cookie);
        let ca = decodedCookie.split(';');
        for(let i = 0; i <ca.length; i++) {
          let c = ca[i];
          while (c.charAt(0) == ' ') {
            c = c.substring(1);
          }
          if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
          }
        }
        return "";
    }

	handleClickOnOpenAccount= () => {
		this.setState({
			showOpenAccount: true,
            showMerchantRegistration : false
		});
	};

    handleClickOnMerchantRegistration =()=>{
        this.setState({
            showOpenAccount: false,
			showMerchantRegistration: true,

		});
    }

	componentDidMount() {
		
        this.setState({ paymentId: this.getCookie("paymentId") });
     
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
            webshopId: this.getCookie("webShopId"),
			totalPrice: this.getCookie("totalPrice")
        };
        
                Axios.post(BASE_URL + "/payment/confirm/" + this.state.paymentId, dto, { validateStatus: () => true })
                    .then((res) => {
                        if (res.status === 409) {
                            this.setState({
                                errorHeader: "Resource conflict!",
                                hiddenErrorAlert: false,
                            });
                        } else if (res.status === 500) {
                            this.setState({ errorHeader: "Internal server error!", errorMessage: "Server error.", hiddenErrorAlert: false });
                        } else {
                            console.log("Success");
                            console.log(res.data)
                            this.setState({showForm:false})

                        }
                    })
                    .catch((err) => {
                        console.log(err);
                    });
            
        
    }
   
	render() {
        if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;

		return (
            <React.Fragment>
				<TopBar />
                <header id="header" className="fixed-top">
              
				<div className="container d-flex align-items-center">
                <h1 className="logo mr-auto">
						<Link to="">Adiko Bank</Link>
					</h1>
					<nav className="nav-menu d-none d-lg-block">
						<ul>
							<li className="active">
								<Link to="/">Home</Link>
							</li>
                           
							
						</ul>
					</nav>
				</div>
			</header>  

<div>
        <div className="container d-flex align-items-center" style={{ marginTop: "10%" }}>
					<div className="row section-design"  style={{  margin: "auto",
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
                            <h5 className=" text-center  mb-0 text-uppercase" style={{ marginTop: "2rem" }}>
					ADIKO BANK
					</h5>
							<form hidden={!this.state.showForm} id="contactForm" name="sentMessage" noValidate="novalidate">
							<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Card number(PAN):*</label>
										<input
											class="form-control"
											type="text"
											id="name"
											onChange={this.handlePANChange}
											value={this.state.pan}
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



                          


						</div>
                        </div>

        </div>
      

        
    </div>
            </React.Fragment>

		);
	}
}

export default Bank1User;
