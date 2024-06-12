import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Utilisateur from './utilisateur';
import Annonce from './annonce';
import Trajet from './trajet';
import Location from './location';
import DeclarationVol from './declaration-vol';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="utilisateur/*" element={<Utilisateur />} />
        <Route path="annonce/*" element={<Annonce />} />
        <Route path="trajet/*" element={<Trajet />} />
        <Route path="location/*" element={<Location />} />
        <Route path="declaration-vol/*" element={<DeclarationVol />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
