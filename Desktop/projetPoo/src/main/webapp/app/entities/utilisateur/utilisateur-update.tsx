import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { getEntity, updateEntity, createEntity, reset } from './utilisateur.reducer';

export const UtilisateurUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const utilisateurEntity = useAppSelector(state => state.utilisateur.entity);
  const loading = useAppSelector(state => state.utilisateur.loading);
  const updating = useAppSelector(state => state.utilisateur.updating);
  const updateSuccess = useAppSelector(state => state.utilisateur.updateSuccess);

  const handleClose = () => {
    navigate('/utilisateur' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...utilisateurEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...utilisateurEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gofindApp.utilisateur.home.createOrEditLabel" data-cy="UtilisateurCreateUpdateHeading">
            Créer ou éditer un Utilisateur
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="utilisateur-id" label="Id" validate={{ required: true }} /> : null}
              <ValidatedField label="Nom" id="utilisateur-nom" name="nom" data-cy="nom" type="text" />
              <ValidatedField label="Prenom" id="utilisateur-prenom" name="prenom" data-cy="prenom" type="text" />
              <ValidatedField label="Email" id="utilisateur-email" name="email" data-cy="email" type="text" />
              <ValidatedField label="Telephone" id="utilisateur-telephone" name="telephone" data-cy="telephone" type="text" />
              <ValidatedField label="Password" id="utilisateur-password" name="password" data-cy="password" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/utilisateur" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Retour</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Sauvegarder
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default UtilisateurUpdate;
