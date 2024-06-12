import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DeclarationVol from './declaration-vol';
import DeclarationVolDetail from './declaration-vol-detail';
import DeclarationVolUpdate from './declaration-vol-update';
import DeclarationVolDeleteDialog from './declaration-vol-delete-dialog';

const DeclarationVolRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DeclarationVol />} />
    <Route path="new" element={<DeclarationVolUpdate />} />
    <Route path=":id">
      <Route index element={<DeclarationVolDetail />} />
      <Route path="edit" element={<DeclarationVolUpdate />} />
      <Route path="delete" element={<DeclarationVolDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DeclarationVolRoutes;
