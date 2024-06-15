import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { getEntities as getUtilisateurs } from 'app/entities/utilisateur/utilisateur.reducer';
import { ITrajet } from 'app/shared/model/trajet.model';
import { getEntity, updateEntity, createEntity, reset } from './trajet.reducer';

export const TrajetUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const utilisateurs = useAppSelector(state => state.utilisateur.entities);
  const trajetEntity = useAppSelector(state => state.trajet.entity);
  const loading = useAppSelector(state => state.trajet.loading);
  const updating = useAppSelector(state => state.trajet.updating);
  const updateSuccess = useAppSelector(state => state.trajet.updateSuccess);

  const handleClose = () => {
    navigate('/trajet' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

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
    if (values.nombreplace !== undefined && typeof values.nombreplace !== 'number') {
      values.nombreplace = Number(values.nombreplace);
    }
    if (values.prixplace !== undefined && typeof values.prixplace !== 'number') {
      values.prixplace = Number(values.prixplace);
    }

    const entity = {
      ...trajetEntity,
      ...values,
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
      ? {}
      : {
          ...trajetEntity,
          utilisateur: trajetEntity?.utilisateur?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gofindApp.trajet.home.createOrEditLabel" data-cy="TrajetCreateUpdateHeading">
            Créer ou éditer un Trajet
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="trajet-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Date" id="trajet-date" name="date" data-cy="date" type="date" />
              <ValidatedField label="Heuredepart" id="trajet-heuredepart" name="heuredepart" data-cy="heuredepart" type="text" />
              <ValidatedField label="Heurearrivee" id="trajet-heurearrivee" name="heurearrivee" data-cy="heurearrivee" type="text" />
              <ValidatedField label="Lieudepart" id="trajet-lieudepart" name="lieudepart" data-cy="lieudepart" type="text" />
              <ValidatedField label="Lieuarrivee" id="trajet-lieuarrivee" name="lieuarrivee" data-cy="lieuarrivee" type="text" />
              <ValidatedField label="Nombreplace" id="trajet-nombreplace" name="nombreplace" data-cy="nombreplace" type="text" />
              <ValidatedField label="Prixplace" id="trajet-prixplace" name="prixplace" data-cy="prixplace" type="text" />
              <ValidatedBlobField
                label="Image Vehicule"
                id="trajet-imageVehicule"
                name="imageVehicule"
                data-cy="imageVehicule"
                isImage
                accept="image/*"
              />
              <ValidatedField id="trajet-utilisateur" name="utilisateur" data-cy="utilisateur" label="Utilisateur" type="select">
                <option value="" key="0" />
                {utilisateurs
                  ? utilisateurs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/trajet" replace color="info">
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

export default TrajetUpdate;
