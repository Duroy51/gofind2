import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITrajet } from 'app/shared/model/trajet.model';
import { getEntities as getTrajets } from 'app/entities/trajet/trajet.reducer';
import { ILocation } from 'app/shared/model/location.model';
import { getEntities as getLocations } from 'app/entities/location/location.reducer';
import { IDeclarationVol } from 'app/shared/model/declaration-vol.model';
import { getEntities as getDeclarationVols } from 'app/entities/declaration-vol/declaration-vol.reducer';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { getEntities as getUtilisateurs } from 'app/entities/utilisateur/utilisateur.reducer';
import { IAnnonce } from 'app/shared/model/annonce.model';
import { getEntity, updateEntity, createEntity, reset } from './annonce.reducer';

export const AnnonceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const trajets = useAppSelector(state => state.trajet.entities);
  const locations = useAppSelector(state => state.location.entities);
  const declarationVols = useAppSelector(state => state.declarationVol.entities);
  const utilisateurs = useAppSelector(state => state.utilisateur.entities);
  const annonceEntity = useAppSelector(state => state.annonce.entity);
  const loading = useAppSelector(state => state.annonce.loading);
  const updating = useAppSelector(state => state.annonce.updating);
  const updateSuccess = useAppSelector(state => state.annonce.updateSuccess);

  const handleClose = () => {
    navigate('/annonce' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTrajets({}));
    dispatch(getLocations({}));
    dispatch(getDeclarationVols({}));
    dispatch(getUtilisateurs({}));
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
    values.datecreation = convertDateTimeToServer(values.datecreation);

    const entity = {
      ...annonceEntity,
      ...values,
      trajet: trajets.find(it => it.id.toString() === values.trajet?.toString()),
      location: locations.find(it => it.id.toString() === values.location?.toString()),
      declarationVol: declarationVols.find(it => it.id.toString() === values.declarationVol?.toString()),
      utilisateur: utilisateurs.find(it => it.id.toString() === values.utilisateur?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          datecreation: displayDefaultDateTime(),
        }
      : {
          ...annonceEntity,
          datecreation: convertDateTimeFromServer(annonceEntity.datecreation),
          trajet: annonceEntity?.trajet?.id,
          location: annonceEntity?.location?.id,
          declarationVol: annonceEntity?.declarationVol?.id,
          utilisateur: annonceEntity?.utilisateur?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gofindApp.annonce.home.createOrEditLabel" data-cy="AnnonceCreateUpdateHeading">
            Créer ou éditer un Annonce
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="annonce-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Titre" id="annonce-titre" name="titre" data-cy="titre" type="text" />
              <ValidatedField label="Description" id="annonce-description" name="description" data-cy="description" type="text" />
              <ValidatedField label="Type" id="annonce-type" name="type" data-cy="type" type="text" />
              <ValidatedField label="Statut" id="annonce-statut" name="statut" data-cy="statut" type="text" />
              <ValidatedField
                label="Datecreation"
                id="annonce-datecreation"
                name="datecreation"
                data-cy="datecreation"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="annonce-trajet" name="trajet" data-cy="trajet" label="Trajet" type="select">
                <option value="" key="0" />
                {trajets
                  ? trajets.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="annonce-location" name="location" data-cy="location" label="Location" type="select">
                <option value="" key="0" />
                {locations
                  ? locations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="annonce-declarationVol"
                name="declarationVol"
                data-cy="declarationVol"
                label="Declaration Vol"
                type="select"
              >
                <option value="" key="0" />
                {declarationVols
                  ? declarationVols.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="annonce-utilisateur" name="utilisateur" data-cy="utilisateur" label="Utilisateur" type="select">
                <option value="" key="0" />
                {utilisateurs
                  ? utilisateurs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/annonce" replace color="info">
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

export default AnnonceUpdate;
