import React, { Component } from "react";
import qr from '../Static/img/qr.png';
import bank_cards from '../Static/img/bank_cards.jpg';
import bitcoin from '../Static/img/bitcoin.png';
import paypal from '../Static/img/paypall.png';
import { useState } from 'react';
import '../App.css';

import { Link } from 'react-router-dom';

const ChoosePayment = () =>{
    
    const [isPaypalAllowed, setIsPaypalAllowed] = useState(true);
    const [isBankCardAllowed, setIsBankCardAllowed] = useState(true);
    const [isQRAllowed, setIsQRAllowed] = useState(true);
    const [isBitcoinAllowed, setIsBitcoinAllowed] = useState(true);

		return (
            <div className="App">
            <header className="App-header">
              <p>
                CHOOSE PAYMENT METHOD
              </p>
              <div class="container">
                <div class="row">
                  {isPaypalAllowed === true && 
                    <div class="col">
                        <img src={paypal} className="App-logo" alt="logo" />
                        <Link to="/payPal">
                           <button type="button" class="btn btn-secondary btn-lg" data-toggle="button" aria-pressed="false" autocomplete="off"> PayPal!</button>
                        </Link>

                    </div>
                  }
                  {isBankCardAllowed === true && 
                    <div class="col">
                        <img src={bank_cards} className="App-logo" alt="logo" />
                        <Link to="/bankCard">
                          <button type="button" class="btn btn-secondary btn-lg" data-toggle="button" aria-pressed="false" autocomplete="off"> Bank cards!</button>
                        </Link>
                    </div>
                  }
                  {isQRAllowed === true && 
                    <div class="col">
                        <img src={qr} className="App-logo" alt="logo" />
                        <Link to="/qrCode">
                          <button type="button" class="btn btn-secondary btn-lg" data-toggle="button" aria-pressed="false" autocomplete="off"> QR code!</button>
                        </Link>

                    </div>
                  }
                  {isBitcoinAllowed === true && 
                    <div class="col">
                        <img src={bitcoin} className="App-logo" alt="logo" />
                        <Link to="/bitCoin">
                          <button type="button" class="btn btn-secondary btn-lg" data-toggle="button" aria-pressed="false" autocomplete="off"> BitCoin!</button>
                        </Link>

                    </div>
                  }
              </div>
            </div>
            </header>
          </div>
		);
	}


export default ChoosePayment;