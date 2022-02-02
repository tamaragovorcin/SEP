import "./App.css";
import HomePage from "./pages/HomePage";
import { HashRouter as Router, Link, Switch, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import BankProfile from "./pages/BankProfile";
import Bank2User from "./pages/Bank2User";
import Bank1User from "./pages/Bank1User";


function App() {
	return (
		<Router>
			<Switch>
				<Link exact to="/" path="/" component={HomePage} />
				<Link exact to="/login" path="/login" component={LoginPage} />
				<Link exact to="/registration" path="/registration" component={RegisterPage} />
				<Link exact to="/bank2" path="/bank2" component={Bank2User} />
				<Link exact to="/bank1" path="/bank1" component={Bank1User} />
				<Route path="/bank/:id" children={<BankProfile/>} />

			</Switch>
		</Router>
	);
}

export default App;
