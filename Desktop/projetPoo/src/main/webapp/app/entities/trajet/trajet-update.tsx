import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITrajet } from 'app/shared/model/trajet.model';
import { getEntity, updateEntity, createEntity, reset } from './trajet.reducer';

export const TrajetUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

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
    values.date = convertDateTimeToServer(values.date);
    values.heuredepart = convertDateTimeToServer(values.heuredepart);
    values.heurearrivee = convertDateTimeToServer(values.heurearrivee);
    if (values.nombreplace !== undefined && typeof values.nombreplace !== 'number') {
      values.nombreplace = Number(values.nombreplace);
    }
    if (values.prixplace !== undefined && typeof values.prixplace !== 'number') {
      values.prixplace = Number(values.prixplace);
    }

    const entity = {
      ...trajetEntity,
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
      ? {
          date: displayDefaultDateTime(),
          heuredepart: displayDefaultDateTime(),
          heurearrivee: displayDefaultDateTime(),
        }
      : {
          ...trajetEntity,
          date: convertDateTimeFromServer(trajetEntity.date),
          heuredepart: convertDateTimeFromServer(trajetEntity.heuredepart),
          heurearrivee: convertDateTimeFromServer(trajetEntity.heurearrivee),
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
              <ValidatedField
                label="Date"
                id="trajet-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Heuredepart"
                id="trajet-heuredepart"
                name="heuredepart"
                data-cy="heuredepart"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Heurearrivee"
                id="trajet-heurearrivee"
                name="heurearrivee"
                data-cy="heurearrivee"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Lieudepart" id="trajet-lieudepart" name="lieudepart" data-cy="lieudepart" type="text" />
              <ValidatedField label="Lieuarrivee" id="trajet-lieuarrivee" name="lieuarrivee" data-cy="lieuarrivee" type="text" />
              <ValidatedField label="Nombreplace" id="trajet-nombreplace" name="nombreplace" data-cy="nombreplace" type="text" />
              <ValidatedField label="Prixplace" id="trajet-prixplace" name="prixplace" data-cy="prixplace" type="text" />
              <ValidatedField
                label="Marque Vehicule"
                id="trajet-marqueVehicule"
                name="marqueVehicule"
                data-cy="marqueVehicule"
                type="text"
              />
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
