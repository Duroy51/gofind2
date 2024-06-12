import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDeclarationVol } from 'app/shared/model/declaration-vol.model';
import { getEntity, updateEntity, createEntity, reset } from './declaration-vol.reducer';

export const DeclarationVolUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const declarationVolEntity = useAppSelector(state => state.declarationVol.entity);
  const loading = useAppSelector(state => state.declarationVol.loading);
  const updating = useAppSelector(state => state.declarationVol.updating);
  const updateSuccess = useAppSelector(state => state.declarationVol.updateSuccess);

  const handleClose = () => {
    navigate('/declaration-vol' + location.search);
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
    values.datevol = convertDateTimeToServer(values.datevol);

    const entity = {
      ...declarationVolEntity,
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
          datevol: displayDefaultDateTime(),
        }
      : {
          ...declarationVolEntity,
          datevol: convertDateTimeFromServer(declarationVolEntity.datevol),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gofindApp.declarationVol.home.createOrEditLabel" data-cy="DeclarationVolCreateUpdateHeading">
            Créer ou éditer un Declaration Vol
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="declaration-vol-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Type" id="declaration-vol-type" name="type" data-cy="type" type="text" />
              <ValidatedField label="Marque" id="declaration-vol-marque" name="marque" data-cy="marque" type="text" />
              <ValidatedField label="Modele" id="declaration-vol-modele" name="modele" data-cy="modele" type="text" />
              <ValidatedField label="Numeroserie" id="declaration-vol-numeroserie" name="numeroserie" data-cy="numeroserie" type="text" />
              <ValidatedField
                label="Datevol"
                id="declaration-vol-datevol"
                name="datevol"
                data-cy="datevol"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/declaration-vol" replace color="info">
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

export default DeclarationVolUpdate;
