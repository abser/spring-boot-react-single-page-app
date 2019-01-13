
import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Route, Link } from 'react-router-dom'
import Upload from './Upload';
import {
  Collapse,
  Navbar,
  NavbarToggler,
  NavbarBrand,
  Nav,
  NavItem,
  NavLink
} from 'reactstrap';

export default class App extends React.Component {
  constructor(props) {
    super(props);

    this.toggle = this.toggle.bind(this);
    this.state = {
      isOpen: false
    };
  }

  toggle() {
    this.setState({
      isOpen: !this.state.isOpen
    });
  }

  render() {
    return (
      <Router>
        <div>
          <Navbar color="light" light expand="md">
            <NavbarBrand href="/">LifeChain</NavbarBrand>
            <NavbarToggler onClick={this.toggle} />
            <Collapse isOpen={this.state.isOpen} navbar>
              <Nav className="ml-auto" navbar>
                <NavItem>
                  <NavLink tag={Link} to="/upload">Upload</NavLink>
                </NavItem>
                <NavItem>
                  <NavLink tag={Link} to="/help">Help</NavLink>
                </NavItem>
              </Nav>
            </Collapse>
          </Navbar>
{/* React Component Routes */}
          <Route exact path="/" component={()=> null} />
          <Route path="/upload" component={Upload} />
          <Route path="/help" component={()=> {
            return(<div>Help? Email to abser.it@gmail.com </div>)
          }} />
        </div>
      </Router>
    )
  }
}
