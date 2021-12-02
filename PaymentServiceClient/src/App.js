import ChoosePayment from './Components/ChoosePayment';
import PayPal from './Components/PayPal/PayPal';
import QRCode from './Components/QRcode/QRCode';
import BankCard from './Components/BankCards/BankCard';
import BitCoin from './Components/Bitcoin/BitCoin';
import PayPalSuccess from './Components/PayPal/PayPalSuccess';
import PayPalError from './Components/PayPal/PayPalError';
import PayPalParams from './Components/PayPal/PayPalParams';

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
          <Link exact to="/payPalParams" path="/payPalParams" component={PayPalParams} />

        </Switch>
     </Router>
  );
}

export default App;
