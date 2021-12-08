import React, { useState, useEffect } from "react";
import { loadStripe } from "@stripe/stripe-js";
import { Elements } from "@stripe/react-stripe-js";

import { BASE_URL_BANK } from "../constants.js";
import Axios from "axios";
import CheckoutForm from "./CheckoutForm";
import "../App.css";
import getAuthHeader from "../GetHeader";

// Make sure to call loadStripe outside of a component’s render to avoid
// recreating the Stripe object on every render.
// This is your test publishable API key.
const stripePromise = loadStripe("pk_test_51K43vfCY3zNGAGpLz1eYNNh5vcUn5n3MSNQNYh789Gm9BvT8NJubPLKXLUb0tdPhVDzovZWlzF9dk6sFGq2l2WNe00rU2oDg4n");

export default function App() {
  const [clientSecret, setClientSecret] = useState("");

  useEffect(() => {
    // Create PaymentIntent as soon as the page loads body: JSON.stringify({ items: [{ id: "xl-tshirt" }] }
    let items= { id: "xl-tshirt" }
    Axios.post("http://localhost:9090/bank-card-service/api/bankcard/create-payment-intent",  items, { headers: { "Content-Type": "application/json" }})
    .then((res) => {

     console.log(res.data)
      setClientSecret(res.data.clientSecret)
    })
   
    
     
			
		
  }, []);

  const appearance = {
    theme: 'stripe',
  };
  const options = {
    clientSecret,
    appearance,
  };

  return (
    <div className="App">
      {clientSecret && (
        <Elements options={options} stripe={stripePromise}>
         <CheckoutForm></CheckoutForm>
        </Elements>
      )}
    </div>
  );
}