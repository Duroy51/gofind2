import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Annonce from './annonce';
import AnnonceDetail from './annonce-detail';
import AnnonceUpdate from './annonce-update';
import AnnonceDeleteDialog from './annonce-delete-dialog';

const AnnonceRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Annonce />} />
    <Route path="new" element={<AnnonceUpdate />} />
    <Route path=":id">
      <Route index element={<AnnonceDetail />} />
      <Route path="edit" element={<AnnonceUpdate />} />
      <Route path="delete" element={<AnnonceDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AnnonceRoutes;
