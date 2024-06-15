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
import { IObjetVolee } from 'app/shared/model/objet-volee.model';
import { getEntity, updateEntity, createEntity, reset } from './objet-volee.reducer';

export const ObjetVoleeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const utilisateurs = useAppSelector(state => state.utilisateur.entities);
  const objetVoleeEntity = useAppSelector(state => state.objetVolee.entity);
  const loading = useAppSelector(state => state.objetVolee.loading);
  const updating = useAppSelector(state => state.objetVolee.updating);
  const updateSuccess = useAppSelector(state => state.objetVolee.updateSuccess);

  const handleClose = () => {
    navigate('/objet-volee' + location.search);
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

    const entity = {
      ...objetVoleeEntity,
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
          ...objetVoleeEntity,
          utilisateur: objetVoleeEntity?.utilisateur?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gofindApp.objetVolee.home.createOrEditLabel" data-cy="ObjetVoleeCreateUpdateHeading">
            Créer ou éditer un Objet Volee
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="objet-volee-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Type" id="objet-volee-type" name="type" data-cy="type" type="text" />
              <ValidatedField label="Marque" id="objet-volee-marque" name="marque" data-cy="marque" type="text" />
              <ValidatedField label="Datevol" id="objet-volee-datevol" name="datevol" data-cy="datevol" type="date" />
              <ValidatedBlobField
                label="Photo Objet"
                id="objet-volee-photoObjet"
                name="photoObjet"
                data-cy="photoObjet"
                isImage
                accept="image/*"
              />
              <ValidatedField label="Numero Serie" id="objet-volee-numeroSerie" name="numeroSerie" data-cy="numeroSerie" type="text" />
              <ValidatedField id="objet-volee-utilisateur" name="utilisateur" data-cy="utilisateur" label="Utilisateur" type="select">
                <option value="" key="0" />
                {utilisateurs
                  ? utilisateurs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/objet-volee" replace color="info">
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

export default ObjetVoleeUpdate;
