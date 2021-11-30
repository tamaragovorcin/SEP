import React, { Component } from "react";
import '../Static/css/paypal_style.css';
import { useState } from 'react';
import axios from "axios";

const PayPal = () => {

  const [price, setPrice] = useState("");
  const [currency, setCurrency] = useState("");
  const [paymentMethod, setPaymentMethod] = useState("");
  const [description, setDescription] = useState("");
  const [intent, setIntent] = useState("");

  const handlePriceChange = event => setPrice(event.target.value);
  const handleCurrencyChange = event => setCurrency(event.target.value);
  const handlePaymentMethodChange = event => setPaymentMethod(event.target.value);
  const handleDescriptionChange = event => setDescription(event.target.value);
  const handleIntentChange = event => setIntent(event.target.value);

  const continueToCheckoutHandler = ()=> {
      console.log(price);
      console.log(currency)
      console.log(paymentMethod)
      console.log(intent)
      console.log(description)
      const checkoutDTO = {
          price : price,
          currency : currency,
          method : paymentMethod,
          intent : intent,
          description : description
      }
      axios.post("http://localhost:9090/paypal-service-api/paypal/pay", checkoutDTO)
          .then( res =>
               console.log(res),
               //location.href = res.data
          )
          .catch(err => console.log(err));
  }

  return (
      <div>
          <div className="row">
          <div className="col-75">
              <div className="container">
              <div>
                  <div className="col-50">
                  <h3>Payment</h3>
                  <label>Accepted Cards</label>
                  <div className="icon-container">
                      <i className="fa fa-cc-visa" style={{ color: "navy" }} />
                      <i className="fa fa-cc-amex" style={{ color: "blue" }} />
                      <i className="fa fa-cc-mastercard" style={{ color: "red" }} />
                      <i className="fa fa-cc-discover" style={{ color: "orange" }} />
                  </div>
                  <label htmlFor="price">Total</label>
                  <input 
                      type="text" 
                      id="price" 
                      name="price" 
                      placeholder="Enter total price"
                      value = {price} 
                      onChange= {handlePriceChange} 
                   />
                  <label htmlFor="currency">Currency</label>
                  <input
                      type="text"
                      id="currency"
                      name="currency"
                      placeholder="Enter Currency"
                      value = {currency} 
                      onChange= {handleCurrencyChange} 
                  />
                  <label htmlFor="method">Payment Method</label>
                  <input
                      type="text"
                      id="method"
                      name="method"
                      placeholder="Payment Method - CREDIT_CARD, PAYPAL, BANK, CARRIER, ALTERNATE_PAYMENT, PAY_UPON_INVOICE"
                      value = {paymentMethod} 
                      onChange= {handlePaymentMethodChange} 
                  />
                  <label htmlFor="intent">Intent</label>
                  <input 
                      type="text" 
                      id="intent"
                      name="intent" 
                      placeholder="Enter intent - SALE, AUTHORIZE, ORDER, NONE"
                      value = {intent} 
                      onChange= {handleIntentChange} 
                  />
                  <label htmlFor="description">Payment Description</label>
                  <input
                      type="text"
                      id="description"
                      name="description"
                      placeholder="Payment Description"
                      value = {description} 
                      onChange= {handleDescriptionChange} 
                  />
                  </div>
                  <button
                      onClick= { continueToCheckoutHandler}
                      className="btn"
                  >Continue to checkout</button>
              </div>
              </div>
          </div>
          <div className="col-25">
              <div className="container">
              <h4>
                  Cart{" "}
                  <span className="price" style={{ color: "black" }}>
                  <i className="fa fa-shopping-cart" /> <b>4</b>
                  </span>
              </h4>
              <p>
                  <a href="#">Product 1</a> <span className="price">$1</span>
              </p>
              <p>
                  <a href="#">Product 2</a> <span className="price">$4</span>
              </p>
              <p>
                  <a href="#">Product 3</a> <span className="price">$3</span>
              </p>
              <p>
                  <a href="#">Product 4</a> <span className="price">$2</span>
              </p>
              <hr />
              <p>
                  Total{" "}
                  <span className="price" style={{ color: "black" }}>
                  <b>$10</b>
                  </span>
              </p>
              </div>
          </div>
          </div>

      </div>
  );
};

export default PayPal;