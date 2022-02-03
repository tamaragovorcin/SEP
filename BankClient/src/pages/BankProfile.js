import React, { Component } from "react";
import TopBar from "../components/TopBar";
import Axios from "axios";
import { BASE_URL } from "../constants.js";
import { Button } from "react-bootstrap";
import { withRouter } from 'react-router-dom';
import { Redirect } from "react-router-dom";
import HeadingAlert from "../components/HeadingAlert";
import { Link } from "react-router-dom";
import OpenAccount from "../components/OpenAccount";
import MerchantRegistration from "../components/MerchantRegistration";

class BankProfile extends Component {
	state = {
		bank : {},
		formShowed: false,	
		redirect: false,
		redirectUrl: "",
		date : "",
        id : "",
        showOpenAccount : false,
        showMerchantRegistration : false
		
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
	
	componentDidMount() {
        const id = this.props.match.params.id;
		this.setState({
			id: id
		})
		let help = []
		Axios.get(BASE_URL + "/api/bank/"+id)
			.then((res) => {
				this.setState({
                  bank : res.data,
                });
                
			})
			.catch((err) => {
				console.log(err);
			});
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
	render() {
		if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;

		return (
			<React.Fragment>
				<TopBar />
                <header id="header" className="fixed-top">
              
				<div className="container d-flex align-items-center">
                <h1 className="logo mr-auto">
						<Link to="">{this.state.bank.name}</Link>
					</h1>
					<nav className="nav-menu d-none d-lg-block">
						<ul>
							<li className="active">
								<Link to="/">Home</Link>
							</li>
                            <li className="active">
								<Link 
                             	onClick={() => this.handleClickOnOpenAccount()}

                                >Open an account</Link>
							</li>
                            <li className="active">
								<Link 
                             	onClick={() => this.handleClickOnMerchantRegistration()}

                                >Merchant Registration</Link>
							</li>
							
						</ul>
					</nav>
				</div>
			</header>  
            <div id="hero">
                <div id= "center" hidden={!this.state.showOpenAccount}>
                <OpenAccount
                    bank={this.state.bank}
                    ></OpenAccount>
                </div>
                   
                <div div = "center" hidden={!this.state.showMerchantRegistration}> 
                <MerchantRegistration
                    bank={this.state.bank}
                ></MerchantRegistration>
                </div>
                   
            </div>
            
               
				
			</React.Fragment>
		);
	}
}

export default withRouter(BankProfile);