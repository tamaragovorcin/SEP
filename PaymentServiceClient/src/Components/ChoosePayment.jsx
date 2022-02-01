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
       let totalPrice = getCookie("totalPrice");
       let webShopId = getCookie("webShopId");
       let paymentId = getCookie("paymentId");
       let webshopType = getCookie("webshopType");

       localStorage.setItem("totalPrice", JSON.stringify(totalPrice));
			 localStorage.setItem("webShopId", JSON.stringify(webShopId));
       localStorage.setItem("paymentId", JSON.stringify(paymentId));
       localStorage.setItem("webshopType", JSON.stringify(webshopType));

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
    function getCookie(cname) {
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

   const handleBitcoinPayment =()=> {
      let webshopType = getCookie("webshopType");
      let totalPrice = getCookie("totalPrice");

      Axios.get("http://localhost:8085/api/"+webshopType+"/payment/bitcoin")
        .then((res) => {
          const token = res.data
          const checkoutDTO = {
            amount: totalPrice,
            merchant_id: "Id",
            merchant_token: token
          }
  
          Axios.post("http://localhost:9090/bitcoion-service/api/bitcoin/pay", checkoutDTO)
            .then((res) => {
              const data = res.data
              var paymentUrl = data.split(', ')[0];
              window.location.href = paymentUrl;
            }
            )
            .catch(err => console.log(err));
        }
        )
        .catch(err => console.log(err));
  
    }

    const handlePayPalPayment = ()=> {
      let webshopType = getCookie("webshopType");
      let totalPrice = getCookie("totalPrice");

      Axios.get("http://localhost:8085/api/"+webshopType+"/payment/paypal")
			.then((res) => {
				const data = res.data
				const checkoutDTO = {
					price: totalPrice,
					currency: "USD",
					method: "PAYPAL",
					intent: "SALE",
					description: "description",
					clientId: data.clientId,
					clientSecret: data.clientSecret
				}
				Axios.post("http://localhost:9090/paypal-service/api/paypal/pay", checkoutDTO)
					.then((res) => {
						const data = res.data
						window.location.href = data
					}
					)
					.catch(err => console.log(err));
			}
			)
			.catch(err => console.log(err));

    }
  
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
                           <button type="button" class="btn btn-secondary btn-lg" 	onClick={handlePayPalPayment} data-toggle="button" aria-pressed="false" autocomplete="off"> PayPal!</button>

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
                        <button type="button" class="btn btn-secondary btn-lg" 	onClick={handleBitcoinPayment} data-toggle="button" aria-pressed="false" autocomplete="off"> BitCoin!</button>

                    </div>
                  }
              </div>
            </div>
            </header>
          </div>
		);
	}


export default ChoosePayment;