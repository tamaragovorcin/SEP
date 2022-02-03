//import './App.css';
import ChoosePayment from './Components/ChoosePayment';
import PayPal from './Components/PayPal';
import QRCode from './Components/QRCodePayment';
import BankCard from './Components/BankCard';
import BitCoin from './Components/BitCoin';
import Login from './Components/Login';
import Register from './Components/Register';
import DefinePaymentMethods from './Components/DefinePaymentMethods';
import { HashRouter as Router, Link, Switch } from "react-router-dom";
import PayPalParams from './Components/InfoPages/PayPalParams';

function App() {
  return (
      <Router>
        <Switch>
          <Link exact to="/" path="/" component={ChoosePayment} />
          <Link exact to="/payPal" path="/payPal" component={PayPal} />
          <Link exact to="/qrCode" path="/qrCode" component={QRCode} /> 
          <Link exact to="/bankCard" path="/bankCard" component={BankCard} />
          <Link exact to="/bankCard" path="/bankCard" component={BankCard} />
          <Link exact to="/bitCoin" path="/bitCoin" component={BitCoin} />
          <Link exact to="/login" path="/login" component={Login} />
          <Link exact to="/register" path="/register" component={Register} />
          <Link exact to="/definePaymentMethods" path="/definePaymentMethods" component={DefinePaymentMethods} />
          <Link exact to="/payPalParams" path="/payPalParams" component={PayPalParams} />

        </Switch>
     </Router>
  );
}

export default App;
