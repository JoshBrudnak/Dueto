import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Dueto from './Dueto';
import registerServiceWorker from './registerServiceWorker';
import { MuiThemeProvider, createMuiTheme } from "material-ui/styles"

ReactDOM.render(
  <MuiThemeProvider>
    <Dueto/>
  </MuiThemeProvider>
  , document.getElementById('dueto'));

registerServiceWorker();
