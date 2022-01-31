import "./App.css";
import HomePage from "./pages/HomePage";
import { HashRouter as Router, Link, Switch, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import BankProfile from "./pages/BankProfile";


function App() {
	return (
		<Router>
			<Switch>
				<Link exact to="/" path="/" component={HomePage} />
				<Link exact to="/login" path="/login" component={LoginPage} />
				<Link exact to="/registration" path="/registration" component={RegisterPage} />
				
				<Route path="/bank/:id" children={<BankProfile/>} />

			</Switch>
		</Router>
	);
}

export default App;
