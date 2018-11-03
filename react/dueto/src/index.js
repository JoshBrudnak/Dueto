import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Dueto from './Dueto';
import registerServiceWorker from './registerServiceWorker';
import { MuiThemeProvider, createMuiTheme } from "@material-ui/core/styles"

const theme = createMuiTheme({
  //TODO: Create a central theme here!
})

ReactDOM.render(
  <MuiThemeProvider theme={theme}>
    <Dueto style={{backgroundColor: "#e8e8e8"}}/>
  </MuiThemeProvider>
  , document.getElementById('dueto'));

registerServiceWorker();
