import React, { Component } from 'react'
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import ReactDOM from 'react-dom';
import { withRouter } from 'react-router';

import LoginForm from './LoginForm'
import RegisterForm from './RegisterForm'
import Dashboard from './Dashboard'
import PrivateRoute from './common/PrivateRoute'
import { history } from './common/History'

class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {
            customUser: null
        }
    }

    render() {
        return (
            <Router history={history}>
                <Switch>
                    <Route path="/login" exact component={LoginForm} />
                    <Route path="/register" exact component={RegisterForm} />
                    <PrivateRoute exact path="/" component={(props) => <Dashboard {...props} />} />
                </Switch>
            </Router>
        )
    }
}

export default withRouter(Home);