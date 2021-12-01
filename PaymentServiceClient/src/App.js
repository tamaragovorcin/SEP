//import './App.css';
import ChoosePayment from './Components/ChoosePayment';
import PayPal from './Components/PayPal';
import QRCode from './Components/QRCode';
import BankCard from './Components/BankCard';
import BitCoin from './Components/BitCoin';
import PayPalSuccess from './Components/InfoPages/PayPalSuccess';
import PayPalError from './Components/InfoPages/PayPalError';

import { HashRouter as Router, Link, Switch } from "react-router-dom";

function App() {
  return (
      <Router>
        <Switch>
          <Link exact to="/" path="/" component={ChoosePayment} />
          <Link exact to="/payPal" path="/payPal" component={PayPal} />
          <Link exact to="/qrCode" path="/qrCode" component={QRCode} /> 
          <Link exact to="/bankCard" path="/bankCard" component={BankCard} />
          <Link exact to="/bitCoin" path="/bitCoin" component={BitCoin} />
          <Link exact to="/bankCard" path="/bankCard" component={BankCard} />
          <Link exact to="/bitCoin" path="/bitCoin" component={BitCoin} />

          <Link exact to="/payPalSuccess" path="/payPalSuccess" component={PayPalSuccess} />
          <Link exact to="/payPalError" path="/payPalError" component={PayPalError} />
        </Switch>
     </Router>
  );
}

export default App;
