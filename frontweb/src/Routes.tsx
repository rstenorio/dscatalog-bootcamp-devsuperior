import NavBar from "components/Navbar";
import Admin from "pages/Admin";
import Catalog from "pages/Catalog";
import { BrowserRouter, Switch, Route } from "react-router-dom";
import Home from "./pages/Home";

const Routes = () => (
  <BrowserRouter>
    <NavBar />
    <Switch>
      <Route path='/' exact>
        <Home />
      </Route>
      <Route path='/products'>
        <Catalog />
      </Route>
      <Route path='/admin'>
        <Admin />
      </Route>

    </Switch>
  </BrowserRouter>
);

export default Routes;
