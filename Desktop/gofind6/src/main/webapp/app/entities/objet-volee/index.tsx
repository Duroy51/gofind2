import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ObjetVolee from './objet-volee';
import ObjetVoleeDetail from './objet-volee-detail';
import ObjetVoleeUpdate from './objet-volee-update';
import ObjetVoleeDeleteDialog from './objet-volee-delete-dialog';

const ObjetVoleeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ObjetVolee />} />
    <Route path="new" element={<ObjetVoleeUpdate />} />
    <Route path=":id">
      <Route index element={<ObjetVoleeDetail />} />
      <Route path="edit" element={<ObjetVoleeUpdate />} />
      <Route path="delete" element={<ObjetVoleeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ObjetVoleeRoutes;
