import React, { useEffect  } from "react";
import qr from '../Static/img/qr.png';
import bank_cards from '../Static/img/bank_cards.jpg';
import bitcoin from '../Static/img/bitcoin.png';
import paypal from '../Static/img/paypall.png';
import { useState } from 'react';
import '../App.css';
import Axios from "axios";

import { Link } from 'react-router-dom';

const ChoosePayment = () =>{
    
    const [isPaypalAllowed, setIsPaypalAllowed] = useState(false);
    const [isBankCardAllowed, setIsBankCardAllowed] = useState(false);
    const [isQRAllowed, setIsQRAllowed] = useState(false);
    const [isBitcoinAllowed, setIsBitcoinAllowed] = useState(false);


    useEffect(() => {
      let webshopId = JSON.parse(localStorage.getItem("webShopId"));
      Axios.get( "http://localhost:9090/auth-service/api/webshop/allMethods/"+webshopId)
        .then((res) => {
                  let enabled = res.data.methods;
                  let disabled = res.data.disabledMethods;
                  for (let i = 0; i < enabled.length; i++) {
                      if(enabled[i]==="qr") {setIsQRAllowed(true)}
                      if(enabled[i]==="bitcoin") {setIsBitcoinAllowed(true)}
                      if(enabled[i]==="card") {setIsBankCardAllowed(true)}
                      if(enabled[i]==="paypal") {setIsPaypalAllowed(true)}
                  }

                  for (let i = 0; i < disabled.length; i++) {
                    if(disabled[i]==="qr") {setIsQRAllowed(false)}
                    if(disabled[i]==="bitcoin") {setIsBitcoinAllowed(false)}
                    if(disabled[i]==="card") {setIsBankCardAllowed(false)}
                    if(disabled[i]==="paypal") {setIsPaypalAllowed(false)}
                }
        })
        .catch((err) => {
          console.log(err);
    });
    }, []);

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