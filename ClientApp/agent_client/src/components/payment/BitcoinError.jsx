import React from "react";
import { Link } from 'react-router-dom';

const BitcoinError = () => {

    return (
        <React.Fragment>
            <div>
            
                <h2 >
                    Error with payment with bitcoin!
                </h2>
                <Link to="/">Back to web shop</Link>
            </div>
        </React.Fragment>
    );   
}

export default BitcoinError;