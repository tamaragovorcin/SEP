import React from "react";
import { Link } from 'react-router-dom';

const PayPalError = () => {

    return (
        <React.Fragment>
            <div>
            
                <h2 >
                    Error with payment with paypal!
                </h2>
                <Link to="/">Back to web shop</Link>
            </div>
        </React.Fragment>
    );   
}

export default PayPalError;