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
import NewCampaigns from './pages/NewCampaigns'
import Ordered from './pages/Ordered'
import OrderedAdmin from './pages/OrderedAdmin'
import Conferences from './pages/conferences/Conferences'
import ConferencesCart from './pages/conferences/ConferencesCart'
import Conference from './pages/conferences/Conference'
import ConferencesOrders from './pages/conferences/ConferencesOrders';
import PaymentMethod from './pages/PaymentMethod'
import PayPalSuccess from './components/payment/PayPalSuccess';
import PayPalError from './components/payment/PayPalError';
import PayPalParams from './components/payment/PayPalParams';

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
				<Link exact to="/newCampaigns" path="/newCampaigns" component={NewCampaigns} />
				<Link exact to="/conferences" path="/conferences" component={Conferences} />
				<Link exact to="/conferencesCart" path="/conferencesCart" component={ConferencesCart} />
				<Link exact to="/conferencesOrders" path="/conferencesOrders" component={ConferencesOrders} />

				<Route path="/conference/:idConf/:idCart" children={<Conference/>} />


				<Link exact to="/paymentMethod" path="/paymentMethod" component={PaymentMethod} />

				
				<Link exact to="/payPalSuccess" path="/payPalSuccess" component={PayPalSuccess} />
				<Link exact to="/payPalError" path="/payPalError" component={PayPalError} />
				<Link exact to="/payPalParams" path="/payPalParams" component={PayPalParams} />

			</Switch>
	</Router>
  );
}

export default App;
