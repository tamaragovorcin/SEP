import './App.css';
import { HashRouter as Router, Link, Switch, Route  } from "react-router-dom";

import Login from './pages/Login'
import Registration from './pages/Registration'
import Unauthorized from './components/Unauthorized'
import PasswordChange from './pages/PasswordChange'
import Orders from './pages/Orders'
import All from './pages/All'
import AllAgents from './pages/AllAgents'
import Homepage from './pages/Homepage'
import Ordered from './pages/Ordered'
import OrderedAdmin from './pages/OrderedAdmin'
import Conferences from './pages/conferences/Conferences'
import ConferencesCart from './pages/conferences/ConferencesCart'
import Conference from './pages/conferences/Conference'
import ConferencesOrders from './pages/conferences/ConferencesOrders';
import PaymentMethod from './pages/PaymentMethod'
import Payment from './pages/payment'
import PaymentMethodConf from './pages/conferences/PaymentMethodConf';
import ProductPaymentSuccess from './components/payment/ProductPaymentSuccess';
import ProductPaymentFailure from './components/payment/ProductPaymentFailure';
import ProductPaymentError from './components/payment/ProductPaymentError';


import ConferencePaymentSuccess from './components/payment/ConferencePaymentSuccess';
import ConferencePaymentFailure from './components/payment/ConferencePaymentFailure';
import ConferencePaymentError from './components/payment/ConferencePaymentError';

function App() {
  return (
    <Router>
			<Switch>
				<Link exact to="/login" path="/login" component={Login} />
				
				{/* <Link exact to="/" path="/" component={All} /> */}
				<Link exact to="/" path="/" component={Homepage} />
				<Link exact to="/registration" path="/registration" component={Registration} />
				<Link exact to="/unauthorized" path="/unauthorized" component={Unauthorized} />
				<Link exact to="/allAgents" path="/allAgents" component={AllAgents} />
				<Link exact to="/passwordChange" path="/passwordChange" component={PasswordChange} />
				<Link exact to="/all" path="/all" component={All} />
				<Link exact to="/orders" path="/orders" component={Orders} />
				<Link exact to="/ordered" path="/ordered" component={Ordered} />
				<Link exact to="/orderedAdmin" path="/orderedAdmin" component={OrderedAdmin} />
				<Link exact to="/conferences" path="/conferences" component={Conferences} />
				<Link exact to="/conferencesCart" path="/conferencesCart" component={ConferencesCart} />
				<Link exact to="/conferencesOrders" path="/conferencesOrders" component={ConferencesOrders} />
				<Link exact to="/paymentMethodConf" path="/paymentMethodConf" component={PaymentMethodConf} />

				<Route path="/conference/:idConf/:idCart" children={<Conference/>} />


				<Link exact to="/paymentMethod" path="/paymentMethod" component={PaymentMethod} />
				<Link exact to="/payment" path="/payment" component={Payment} />

				<Link exact to="/productPaymentFailure" path="/productPaymentFailure" component={ProductPaymentFailure} />
				<Link exact to="/productPaymentError" path="/productPaymentError" component={ProductPaymentError} />
				<Link exact to="/productPaymentSuccess" path="/productPaymentSuccess" component={ProductPaymentSuccess} />

				<Link exact to="/conferencePaymentSuccess" path="/conferencePaymentSuccess" component={ConferencePaymentSuccess} />
				<Link exact to="/conferencePaymentFailure" path="/conferencePaymentFailure" component={ConferencePaymentFailure} />
				<Link exact to="/conferencePaymentError" path="/conferencePaymentError" component={ConferencePaymentError} />
			</Switch>
	</Router>
  );
}

export default App;
