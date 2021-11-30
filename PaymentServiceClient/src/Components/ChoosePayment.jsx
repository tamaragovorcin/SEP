import React, { Component } from "react";
import '../Static/css/style.css';


import { Link } from 'react-router-dom';

class ChoosePayment extends Component {
    
    handelClickPayPal = () => {
		console.log("PayPal clicked")
        
	};
    handelClickCard = () => {
		console.log("Card clicked")
        
	};

    handelClickQR = () => {
		console.log("QR clicked")
       
	};

    render() {
       
		return (
            <React.Fragment>
                <div>
                    <section class="section-tours" id="section-tours">
                    <div class="u-center-text u-margin-bottom-big">
                        <h2 class="heading-secondary">
                             Choose payment method
                        </h2>
                    </div>

                    <div class="row">
                        <div class="col-1-of-4">
                        <div class="card">
                            <div class="card__side card__side--front">
                                    <div class="card__picture card__picture--1">
                                        &nbsp;
                                    </div>
                                    <h4 class="card__heading">
                                        <span class="card__heading-span card__heading-span--1">Bit Coin</span>
                                    </h4>
                                    <div class="card__details">
                                        <ul>
                                            <li>Visa</li>
                                            <li>Mater card</li>
                                        </ul>
                                    </div>
                                
                            </div>
                            <div class="card__side card__side--back card__side--back-1">
                                    <div class="card__cta">
                                   
                                        <Link href="#popup" class="btn btn--white" to="/bitCoin">Choose!</Link>
                                    </div>
                                </div>
                        </div>
                        </div>


                        <div class="col-1-of-4">
                            <div class="card">
                                <div class="card__side card__side--front">
                                    <div class="card__picture card__picture--2">
                                        &nbsp;
                                    </div>
                                    <h4 class="card__heading">
                                        <span class="card__heading-span card__heading-span--2">QR code</span>
                                    </h4>
                                    <div class="card__details">
                                        <ul>
                                            <li>7 day tours</li>
                                            <li>Up to 40 people</li>
                                            <li>6 tour guides</li>
                                            <li>Sleep in provided tents</li>
                                            <li>Difficulty: medium</li>
                                        </ul>
                                    </div>

                                </div>
                                <div class="card__side card__side--back card__side--back-2">
                                    <div class="card__cta">
                                
                                        <Link href="#popup" class="btn btn--white" to="/qrCode">Choose!</Link>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div class="col-1-of-4">
                            <div class="card">
                                <div class="card__side card__side--front">
                                    <div class="card__picture card__picture--3">
                                        &nbsp;
                                    </div>
                                    <h4 class="card__heading">
                                        <span class="card__heading-span card__heading-span--3">PayPal</span>
                                    </h4>
                                    <div class="card__details">
                                        <ul>
                                            <li>5 day tours</li>
                                            <li>Up to 15 people</li>
                                            
                                        </ul>
                                    </div>

                                </div>
                                <div class="card__side card__side--back card__side--back-3">
                                    <div class="card__cta">
                                       
                                        <Link href="#popup" class="btn btn--white" to="/payPal">Choose!</Link>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-1-of-4">
                            <div class="card">
                                <div class="card__side card__side--front">
                                    <div class="card__picture card__picture--3">
                                        &nbsp;
                                    </div>
                                    <h4 class="card__heading">
                                        <span class="card__heading-span card__heading-span--4">Card</span>
                                    </h4>
                                    <div class="card__details">
                                        <ul>
                                            <li>5 day tours</li>
                                            <li>Up to 15 people</li>
                                           
                                        </ul>
                                    </div>

                                </div>
                                <div class="card__side card__side--back card__side--back-3">
                                    <div class="card__cta">
                                       
                                        <Link href="#popup" class="btn btn--white" to="/bankCard">Choose!</Link>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
            </React.Fragment>
		);
	}
}

export default ChoosePayment;